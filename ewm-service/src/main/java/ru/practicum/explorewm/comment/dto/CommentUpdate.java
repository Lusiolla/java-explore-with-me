package ru.practicum.explorewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdate {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String text;
}
