package ru.practicum.explorewm.requests.mapper;

import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.requests.status.Status;
import ru.practicum.explorewm.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewm.requests.model.ParticipationRequest;
import ru.practicum.explorewm.user.model.User;

import java.time.LocalDateTime;

public class RequestMapper {
    public static ParticipationRequest getNewRequests(User requester, Event event) {
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requester);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        if (!event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }

        return request;
    }

    public static ParticipationRequestDto mapToRequestDto(ParticipationRequest request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(request.getId());
        dto.setCreated(request.getCreated());
        dto.setRequester(request.getRequester().getId());
        dto.setEvent(request.getEvent().getId());
        dto.setStatus(request.getStatus());

        return dto;
    }
}
