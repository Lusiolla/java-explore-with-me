package ru.practicum.explorewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreate {
    @NotBlank
    @Email
    @Pattern(regexp = "^\\S*$")
    private String email;
    @NotNull
    @NotBlank
    private String name;
}
