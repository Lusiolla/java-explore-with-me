package ru.practicum.explorewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShort {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String name;
}
