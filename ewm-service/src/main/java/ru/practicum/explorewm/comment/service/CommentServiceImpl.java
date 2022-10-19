package ru.practicum.explorewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.comment.repository.CommentRepository;
import ru.practicum.explorewm.comment.dto.CommentDto;
import ru.practicum.explorewm.comment.dto.CommentFull;
import ru.practicum.explorewm.comment.dto.CommentUpdate;
import ru.practicum.explorewm.comment.mapper.CommentMapper;
import ru.practicum.explorewm.comment.model.Comment;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.event.repository.EventRepository;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.exception.ConditionsNotMetException;
import ru.practicum.explorewm.exception.ObjectNotFoundException;
import ru.practicum.explorewm.requests.model.ParticipationRequest;
import ru.practicum.explorewm.requests.repository.ParticipationRequestRepository;
import ru.practicum.explorewm.requests.status.Status;
import ru.practicum.explorewm.user.model.User;
import ru.practicum.explorewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final ParticipationRequestRepository requestRepository;

    @Override
    @Transactional
    public CommentDto add(Comment newComment, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        Optional<ParticipationRequest> request = requestRepository.findByRequesterIdAndEventIdAndStatus(
                userId, eventId, Status.CONFIRMED
        );
        if (request.isEmpty()) {
            throw new ConditionsNotMetException("Only participants of the event can add comments.");
        }
        newComment.setAuthor(user);
        newComment.setEventId(eventId);

        return CommentMapper.mapToCommentDto(repository.save(newComment));
    }

    @Override
    @Transactional
    public CommentDto update(CommentUpdate updateComment, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Comment comment = repository.findById(updateComment.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Comment", updateComment.getId()));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConditionsNotMetException("Only the author can update comment.");
        }
        if (comment.getState() == State.PUBLISHED) {
            throw new ConditionsNotMetException("Comment already published.");
        }
        if (comment.getState() == State.CANCELED) {
            comment.setState(State.PENDING);
        }
        comment.setText(updateComment.getText());
        return CommentMapper.mapToCommentDto(repository.save(comment));
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Comment", commentId));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConditionsNotMetException("Only the author can delete comment.");
        }
        repository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteAllByUser(Long userId) {
        repository.deleteByAuthorId(userId);
    }

    @Override
    @Transactional
    public CommentFull publish(Long eventId, Long commentId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("User", eventId));
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Comment", commentId));
        if (event.getState() != State.PUBLISHED) {
            throw new ConditionsNotMetException("The event should be published.");
        }
        comment.setPublishedOn(LocalDateTime.now());
        comment.setState(State.PUBLISHED);
        return CommentMapper.mapToCommentFull(repository.save(comment));
    }

    @Override
    @Transactional
    public CommentFull reject(Long commentId) {
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Comment", commentId));
        comment.setState(State.CANCELED);
        return CommentMapper.mapToCommentFull(repository.save(comment));
    }

    @Override
    @Transactional
    public Collection<CommentFull> getUserComments(Long userId) {
        return repository.findByAuthorId(userId)
                .stream()
                .map(CommentMapper::mapToCommentFull)
                .collect(Collectors.toList());
    }
}
