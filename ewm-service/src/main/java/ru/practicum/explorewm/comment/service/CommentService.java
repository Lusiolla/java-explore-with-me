package ru.practicum.explorewm.comment.service;

import ru.practicum.explorewm.comment.dto.CommentDto;
import ru.practicum.explorewm.comment.dto.CommentUpdate;
import ru.practicum.explorewm.comment.dto.CommentFull;
import ru.practicum.explorewm.comment.model.Comment;

import java.util.Collection;

public interface CommentService {

    CommentDto add(Comment newComment, Long userId, Long eventId);

    CommentDto update(CommentUpdate updateComment, Long userId);

    void delete(Long userId, Long commentId);

    void deleteAllByUser(Long userId);

    CommentFull publish(Long eventId, Long commentId);

    CommentFull reject(Long commentId);

    Collection<CommentFull> getUserComments(Long userId);
}
