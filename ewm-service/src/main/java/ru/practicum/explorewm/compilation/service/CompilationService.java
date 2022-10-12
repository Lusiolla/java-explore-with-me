package ru.practicum.explorewm.compilation.service;

import ru.practicum.explorewm.compilation.dto.CompilationDto;
import ru.practicum.explorewm.compilation.dto.CompilationCreate;

import java.util.Collection;

public interface CompilationService {

    CompilationDto add(CompilationCreate compilation);

    void delete(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);

    Collection<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(Long compId);
}
