package ru.practicum.explorewm.comment.mapper;

import ru.practicum.explorewm.comment.dto.CommentCreate;
import ru.practicum.explorewm.comment.dto.CommentDto;
import ru.practicum.explorewm.comment.dto.CommentFull;
import ru.practicum.explorewm.comment.dto.CommentShort;
import ru.practicum.explorewm.comment.model.Comment;
import ru.practicum.explorewm.event.state.State;

import java.time.LocalDateTime;

public class CommentMapper {

    public static Comment mapToComment(CommentCreate commentCreate) {

        return new Comment(
                null,
                null,
                null,
                commentCreate.getText(),
                LocalDateTime.now(),
                State.PENDING,
                null

        );
    }

    public static CommentFull mapToCommentFull(Comment comment) {

        return new CommentFull(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getEventId(),
                comment.getText(),
                comment.getCreatedOn(),
                comment.getState(),
                comment.getPublishedOn()

        );
    }

    public static CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getEventId(),
                comment.getText(),
                comment.getCreatedOn()
        );
    }

    public static CommentShort mapToCommentShort(Comment comment) {
        return new CommentShort(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getText(),
                comment.getCreatedOn()
        );
    }
}
