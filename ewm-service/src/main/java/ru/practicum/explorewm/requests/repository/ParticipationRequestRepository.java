package ru.practicum.explorewm.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewm.requests.model.ParticipationRequest;
import ru.practicum.explorewm.requests.status.Status;

import java.util.Collection;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long>,
        QuerydslPredicateExecutor<ParticipationRequestRepository> {

    Collection<ParticipationRequest> findAllByRequesterId(long requesterId);

    Collection<ParticipationRequest> findAllByEventId(long eventId);

    Optional<ParticipationRequest> findByRequesterIdAndEventIdAndStatus(long requesterId, long eventId, Status status);
}
