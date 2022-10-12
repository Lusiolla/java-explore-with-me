package ru.practicum.explorewm.compilation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.compilation.dto.CompilationDto;
import ru.practicum.explorewm.compilation.service.CompilationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class PublicCompilationController {

    private final CompilationService service;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                             Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10")
                                             Integer size) {
        return service.getAll(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getById(@NotNull @PathVariable Long compId) {
        return service.getById(compId);
    }


}
