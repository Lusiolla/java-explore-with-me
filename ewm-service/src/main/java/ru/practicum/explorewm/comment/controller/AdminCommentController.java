package ru.practicum.explorewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.comment.dto.CommentFull;
import ru.practicum.explorewm.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@Validated
public class AdminCommentController {

    private final CommentService commentService;

    @PatchMapping("/{commentId}/events/{eventId}/publish")
    public CommentFull publish(@NotNull @PathVariable Long eventId,
                               @NotNull @PathVariable Long commentId) {

        return commentService.publish(eventId, commentId);
    }

    @PatchMapping("/{commentId}/reject")
    public CommentFull reject(@NotNull @PathVariable Long commentId) {

        return commentService.reject(commentId);
    }

    @DeleteMapping("{userId}")
    public void deleteAllByUserId(@NotNull @PathVariable Long userId) {
        commentService.deleteAllByUser(userId);
    }
}
