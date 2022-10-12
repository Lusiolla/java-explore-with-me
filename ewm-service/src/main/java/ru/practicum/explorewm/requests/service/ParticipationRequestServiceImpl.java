package ru.practicum.explorewm.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.event.State;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.event.repository.EventRepository;
import ru.practicum.explorewm.exception.ConditionsNotMetException;
import ru.practicum.explorewm.exception.ObjectNotFoundException;
import ru.practicum.explorewm.requests.ParticipationRequestRepository;
import ru.practicum.explorewm.requests.RequestMapper;
import ru.practicum.explorewm.requests.Status;
import ru.practicum.explorewm.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewm.requests.model.ParticipationRequest;
import ru.practicum.explorewm.user.UserRepository;
import ru.practicum.explorewm.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;


    @Override
    public Collection<ParticipationRequestDto> getUserRequests(Long userId) {
        return repository.findAllByRequesterId(userId)
                .stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto add(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    "The initiator of the event cannot add a request to participate in his event."
            );
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConditionsNotMetException(
                    "You cannot participate in an unpublished event."
            );
        }
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ConditionsNotMetException(
                    "The event has reached the limit of participation requests."
            );
        }
        ParticipationRequest request = RequestMapper.getNewRequests(user, event);
        try {
            repository.save(request);
        } catch (RuntimeException e) {
            throw new ConditionsNotMetException("You cannot add a repeat request to participate in the event.");
        }

        if (request.getStatus() == Status.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        return RequestMapper.mapToRequestDto(request);

    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        ParticipationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request", requestId));
        if (!request.getRequester().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    "Only the requester can cancel the request."
            );
        }
        if (request.getStatus() == Status.CONFIRMED) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(Status.CANCELED);
        return RequestMapper.mapToRequestDto(repository.save(request));
    }

    @Override
    public Collection<ParticipationRequestDto> getByEventIdUserRequests(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    "This is not the event initiator."
            );
        }
        return repository.findAllByEventId(eventId)
                .stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmParticipation(Long userId, Long eventId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        ParticipationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request", requestId));
        if (event.getRequestModeration() && event.getParticipantLimit() == 0) {
            throw new ConditionsNotMetException(
                    "Confirmation of participation is not required."
            );
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    "Only the initiator can confirm participation."
            );
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ConditionsNotMetException("Request limit reached.");
        }

        request.setStatus(Status.CONFIRMED);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);

        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            rejectAllEventRequests(eventId);
        }
        return RequestMapper.mapToRequestDto(repository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectParticipation(Long userId, Long eventId, Long reqId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        ParticipationRequest request = repository.findById(reqId)
                .orElseThrow(() -> new ObjectNotFoundException("Request", reqId));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    "Only the initiator can reject participation."
            );
        }
        if (request.getStatus() == Status.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(Status.REJECTED);
        return RequestMapper.mapToRequestDto(repository.save(request));
    }

    private void rejectAllEventRequests(long eventId) {
        repository.findAllByEventId(eventId).stream().peek(state -> state.setStatus(Status.REJECTED))
                .forEach(repository::save);
    }
}
