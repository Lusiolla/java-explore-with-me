package ru.practicum.explorewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationCreate {
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
    @NotNull
    private Set<Long> events;
}
