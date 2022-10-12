package ru.practicum.explorewm.requests.service;

import ru.practicum.explorewm.requests.dto.ParticipationRequestDto;

import java.util.Collection;

public interface ParticipationRequestService {

    Collection<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto add(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    ParticipationRequestDto getUserRequest(Long userId, Long eventId);

    ParticipationRequestDto confirmParticipation(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectParticipation(Long userId, Long eventId, Long reqId);

}
