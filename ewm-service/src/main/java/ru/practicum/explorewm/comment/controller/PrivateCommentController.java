package ru.practicum.explorewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.comment.dto.CommentCreate;
import ru.practicum.explorewm.comment.dto.CommentDto;
import ru.practicum.explorewm.comment.dto.CommentUpdate;
import ru.practicum.explorewm.comment.mapper.CommentMapper;
import ru.practicum.explorewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
@Validated
public class PrivateCommentController {

    private final CommentService commentService;


    @PostMapping("/events/{eventId}")
    public CommentDto add(@NotNull @PathVariable Long userId,
                          @NotNull @PathVariable Long eventId,
                          @Valid @RequestBody CommentCreate commentCreate) {
        return commentService.add(CommentMapper.mapToComment(commentCreate), userId, eventId);
    }

    @PatchMapping
    public CommentDto update(@NotNull @PathVariable Long userId,
                             @Valid @RequestBody CommentUpdate updateComment) {
        return commentService.update(updateComment, userId);
    }

    @DeleteMapping("{commentId}")
    public void delete(@NotNull @PathVariable Long userId,
                       @NotNull @PathVariable Long commentId) {
        commentService.delete(userId, commentId);
    }
}
