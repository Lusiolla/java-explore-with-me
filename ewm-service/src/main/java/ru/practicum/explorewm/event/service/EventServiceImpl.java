package ru.practicum.explorewm.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.category.repository.CategoryRepository;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.event.mapper.EventMapper;
import ru.practicum.explorewm.event.dto.SortRequest;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.event.dto.*;
import ru.practicum.explorewm.event.location.model.Location;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.event.model.QEvent;
import ru.practicum.explorewm.event.repository.EventRepository;
import ru.practicum.explorewm.event.repository.LocationRepository;
import ru.practicum.explorewm.exception.ConditionsNotMetException;
import ru.practicum.explorewm.exception.ObjectNotFoundException;
import ru.practicum.explorewm.statistics.service.SendingStatisticsService;
import ru.practicum.explorewm.statistics.dto.EndpointHit;
import ru.practicum.explorewm.user.repository.UserRepository;
import ru.practicum.explorewm.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final SendingStatisticsService statisticsService;

    @Override
    public Collection<EventShort> getByParamPublic(PublicGetEventRequest request, HttpServletRequest req) {
        Collection<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(QEvent.event.state.eq(State.PUBLISHED));

        if (request.getText() != null) {
            conditions.add(
                    QEvent.event.annotation.upper().like("%" + request.getText().toUpperCase() + "%")
                            .or(QEvent.event.description.upper().like("%" + request.getText().toUpperCase() + "%"))
            );
        }
        if (request.getCategories() != null || !(request.getCategories().isEmpty())) {
            conditions.add(QEvent.event.category.id.in(request.getCategories()));
        }
        if (request.getPaid() != null) {
            conditions.add(QEvent.event.paid.eq(request.getPaid()));
        }
        if (request.getRangeStart() == null) {
            conditions.add(QEvent.event.eventDate.after(LocalDateTime.now()));
        } else {
            conditions.add(QEvent.event.eventDate.after(request.getRangeStart()));
        }
        if (request.getRangeEnd() != null) {
            conditions.add(QEvent.event.eventDate.before(request.getRangeEnd()));
        }
        if (request.getOnlyAvailable()) {
            conditions.add(QEvent.event.participantLimit.lt(QEvent.event.confirmedRequests));
        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        sendStatistics(req);

        return EventMapper.mapToLisEventShort(repository.findAll(
                finalCondition,
                PageRequest.of(request.getFrom() / request.getSize(), request.getSize(),
                        makeSortCondition(request.getSortRequest()))
        ));

    }

    @Override
    @Transactional
    public EventFull getByIdPublic(long eventId, HttpServletRequest req) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        if (event.getState() != State.PUBLISHED) {
            throw new ConditionsNotMetException("Only published event can be viewed.");
        }

        sendStatistics(req);
        event.setViews(event.getViews() + 1);

        return EventMapper.mapToEventFull(repository.save(event));
    }

    @Override
    public Collection<EventShort> getUserEvents(Long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "User", userId));

        return EventMapper.mapToLisEventShort(repository.findAll(
                QEvent.event.initiator.id.eq(userId), PageRequest.of(from / size, size)));
    }

    @Override
    public EventDto getByIdUserEvent(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "User", userId));
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException("The user is not the creator of this event.");
        }
        return EventMapper.mapToEventDto(event);
    }

    @Override
    @Transactional
    public EventDto add(Long userId, Event event) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "User", userId));
        event.setInitiator(user);
        Category category = categoryRepository.findById(
                event.getCategory().getId()).orElseThrow(() -> new ObjectNotFoundException(
                "Category", event.getCategory().getId()
        ));
        event.setCategory(category);
        event.setLocation(saveOrFindLocation(event.getLocation()));

        return EventMapper.mapToEventDto(repository.save(event));
    }

    @Override
    @Transactional
    public EventDto adminUpdate(Long eventId, AdminEventUpdate event) {
        Event eventFromRepository = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new ObjectNotFoundException("Category", event.getCategory()));
        Event update = EventMapper.mapToEvent(event, eventFromRepository);
        update.setCategory(category);

        return EventMapper.mapToEventDto(repository.save(update));
    }

    @Override
    @Transactional
    public EventDto userUpdate(Long userId, EventUpdate event) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event eventFromRepository = repository.findById(event.getEventId())
                .orElseThrow(() -> new ObjectNotFoundException("Event", event.getEventId()));
        if (!eventFromRepository.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException("The user is not the creator of this event");
        }
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new ObjectNotFoundException("Category", eventFromRepository.getCategory().getId()));

        Event update = EventMapper.mapToEvent(event, eventFromRepository);
        update.setCategory(category);

        switch (update.getState()) {
            case PUBLISHED:
                throw new ConditionsNotMetException("Event already published.");
            case CANCELED:
                update.setState(State.PENDING);
        }

        return EventMapper.mapToEventDto(repository.save(update));
    }

    @Override
    @Transactional
    public EventDto cancel(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException("The user is not the creator of this event");
        }
        if (event.getState() != State.PENDING) {
            throw new ConditionsNotMetException("Event already published or canceled.");
        } else {
            event.setState(State.CANCELED);
        }

        return EventMapper.mapToEventDto(repository.save(event));
    }

    @Override
    public Collection<EventFull> getByParamAdmin(AdminGetEventRequest request) {

        Collection<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(QEvent.event.initiator.id.in(request.getUsers()));
        conditions.add(QEvent.event.category.id.in(request.getCategories()));
        request.getStates().stream().map(QEvent.event.state::eq).forEach(conditions::add);
        conditions.add(QEvent.event.eventDate.after(request.getRangeStart()));
        conditions.add(QEvent.event.eventDate.before(request.getRangeEnd()));

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        return EventMapper.mapToLisEventFull(repository.findAll(
                finalCondition,
                PageRequest.of(request.getFrom() / request.getSize(), request.getSize())
        ));
    }

    @Override
    @Transactional
    public EventPublished publish(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        event.setPublishedOn(LocalDateTime.now());
        if (!event.getPublishedOn().plusHours(1).isBefore(event.getEventDate())) {
            throw new ConditionsNotMetException(
                    "The start date of the event must be no earlier than an hour from the date of publication."
            );
        }
        event.setState(State.PUBLISHED);
        return EventMapper.mapToEventPublished(repository.save(event));
    }

    @Override
    @Transactional
    public EventDto reject(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        if (event.getState() == State.PUBLISHED) {
            throw new ConditionsNotMetException("The event should not be published.");
        }
        event.setState(State.CANCELED);
        return EventMapper.mapToEventDto(repository.save(event));
    }

    private Location saveOrFindLocation(Location location) {
        Optional<Location> locationFromRepository = locationRepository.findByLatAndLon(
                location.getLat(), location.getLon()
        );
        return locationFromRepository.orElseGet(() -> locationRepository.save(location));
    }

    private void sendStatistics(HttpServletRequest request) {
        statisticsService.sendStatistics(EndpointHit.mapToEndpointHit(request));
    }

    private Sort makeSortCondition(SortRequest request) {
        switch (request) {
            case EVENT_DATE:
                return Sort.by("eventDate").descending();
            case VIEWS:
                return Sort.by("views").descending();
            default:
                throw new RuntimeException();
        }
    }
}
