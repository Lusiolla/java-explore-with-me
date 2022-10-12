package ru.practicum.explorewm.requests;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewm.requests.model.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long>, QuerydslPredicateExecutor<ParticipationRequestRepository> {

    Collection<ParticipationRequest> findAllByRequesterId(long requesterId);

    Optional<ParticipationRequest> findByRequesterIdAndEventId(long userId, long eventId);

    Collection<ParticipationRequest> findAllByEventId(long eventId);
}
