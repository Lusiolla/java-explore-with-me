package ru.practicum.explorewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewm.category.CategoryMapper;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.event.dto.*;
import ru.practicum.explorewm.event.location.dto.LocationDto;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.user.UserMapper;
import ru.practicum.explorewm.user.dto.UserShort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class EventMapper {

    // из create в entity
    public static Event mapToEvent(EventCreate request) {
        Event event = new Event();
        Category category = new Category();
        category.setId(request.getCategory());

        event.setEventDate(request.getEventDate());
        event.setCategory(category);
        event.setAnnotation(request.getAnnotation());
        event.setDescription(request.getDescription());
        event.setCreatedOn(LocalDateTime.now());
        event.setLocation(request.getLocation());
        event.setPaid(request.getPaid());
        event.setParticipantLimit(request.getParticipantLimit());
        event.setRequestModeration(request.getRequestModeration());
        event.setState(State.PENDING);
        event.setTitle(request.getTitle());
        return event;
    }

    // из update в entity
    public static Event mapToEvent(EventUpdate request, Event event) {
        event.setAnnotation(request.getAnnotation());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setPaid(request.getPaid());
        event.setParticipantLimit(request.getParticipantLimit());
        event.setTitle(request.getTitle());
        return event;
    }

    // из entity в full
    public static EventFull mapToEventFull(Event event) {
        EventFull response = new EventFull();
        response.setId(event.getId());
        response.setAnnotation(event.getAnnotation());
        response.setCategory(event.getCategory());
        response.setConfirmedRequests(event.getConfirmedRequests());
        response.setEventDate(event.getEventDate());
        response.setDescription(event.getDescription());
        response.setInitiator(new UserShort(
                event.getInitiator().getId(),
                event.getInitiator().getName()
        ));
        response.setLocation(new LocationDto(
                event.getLocation().getLat(),
                event.getLocation().getLon())
        );
        response.setCreatedOn(event.getCreatedOn());
        response.setParticipantLimit(event.getParticipantLimit());
        response.setRequestModeration(event.getRequestModeration());
        response.setPaid(event.getPaid());
        response.setState(event.getState());
        response.setTitle(event.getTitle());
        response.setPublishedOn(event.getPublishedOn());

        return response;
    }

    // из entity в afterUpdate
    public static EventDto mapToEventAfterUpdate(Event event) {
        return new EventDto(
                event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                new UserShort(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()
                ),
                new LocationDto(
                        event.getLocation().getLat(),
                        event.getLocation().getLon()
                ),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle()
        );
    }

    // из entity в published
    public static EventPublished mapToEventPublished(Event event) {
        return new EventPublished(
                event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                new UserShort(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()
                ),
                new LocationDto(
                        event.getLocation().getLat(),
                        event.getLocation().getLon()
                ),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle()
        );

    }

    // из entity в short
    public static EventShort mapToEventShort(Event event) {
        EventShort response = new EventShort();
        response.setId(event.getId());
        response.setAnnotation(event.getAnnotation());
        response.setCategory(CategoryMapper.mapToCategoryDto(event.getCategory()));
        response.setConfirmedRequests(event.getConfirmedRequests());
        response.setEventDate(event.getEventDate());
        response.setInitiator(UserMapper.mapToUserShort(event.getInitiator()));
        response.setPaid(event.getPaid());
        response.setTitle(event.getTitle());
        response.setViews(event.getViews());

        return response;
    }

    // из iterable в full
    public static Collection<EventFull> mapToLisEventFull(Iterable<Event> events) {
        Collection<EventFull> response = new ArrayList<>();
        for (Event event : events) {
            response.add(mapToEventFull(event));
        }
        return response;
    }

    // из iterable в short
    public static Collection<EventShort> mapToLisEventShort(Iterable<Event> events) {
        Collection<EventShort> response = new ArrayList<>();
        for (Event event : events) {
            response.add(mapToEventShort(event));
        }
        return response;
    }
}
