package ru.practicum.explorewm.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewm.requests.model.ParticipationRequest;

import java.util.Collection;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long>, QuerydslPredicateExecutor<ParticipationRequestRepository> {

    Collection<ParticipationRequest> findAllByRequesterId(long requesterId);

    Collection<ParticipationRequest> findAllByEventId(long eventId);
}
