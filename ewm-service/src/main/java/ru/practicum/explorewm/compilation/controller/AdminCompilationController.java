package ru.practicum.explorewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.compilation.dto.CompilationDto;
import ru.practicum.explorewm.compilation.dto.CompilationCreate;
import ru.practicum.explorewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {

    private final CompilationService service;

    @PostMapping
    public CompilationDto add(@Valid @RequestBody CompilationCreate compilation) {
        return service.add(compilation);
    }

    @DeleteMapping("/{compId}")
    public void delete(@NotNull @PathVariable Long compId) {
        service.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@NotNull @PathVariable Long compId,
                                           @NotNull @PathVariable Long eventId) {
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@NotNull @PathVariable Long compId,
                                      @NotNull @PathVariable Long eventId) {
        service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@NotNull @PathVariable Long compId) {
        service.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@NotNull @PathVariable Long compId) {
        service.pinCompilation(compId);
    }
}
