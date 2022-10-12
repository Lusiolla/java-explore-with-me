package ru.practicum.explorewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.compilation.CompilationMapper;
import ru.practicum.explorewm.compilation.CompilationRepository;
import ru.practicum.explorewm.compilation.dto.CompilationDto;
import ru.practicum.explorewm.compilation.dto.CompilationCreate;
import ru.practicum.explorewm.compilation.model.Compilation;
import ru.practicum.explorewm.compilation.model.QCompilation;
import ru.practicum.explorewm.event.repository.EventRepository;
import ru.practicum.explorewm.exception.ObjectNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto add(CompilationCreate compilation) {
        return CompilationMapper.mapToCompilationDto(repository.save(CompilationMapper.mapToCompilation(
                compilation, compilation.getEvents().stream()
                        .map(id -> eventRepository.findById(id)
                                .orElseThrow(() -> new ObjectNotFoundException("Event", id)))
                        .collect(Collectors.toList()))
        ));
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        repository.deleteEventFromCompilation(compId, eventId);
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        repository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Compilation", compId));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event", eventId));
        repository.addEventToCompilation(compId, eventId);
    }

    @Override
    @Transactional
    public void unpinCompilation(Long compId) {
        repository.unpinCompilation(compId);
    }

    @Override
    @Transactional
    public void pinCompilation(Long compId) {
        repository.pinCompilation(compId);
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, int from, int size) {
        Page<Compilation> compilations;
        if (pinned == null) {
            compilations = repository.findAll(PageRequest.of(from / size, size));
        } else {
            compilations = repository.findAll(
                    QCompilation.compilation.pinned.eq(pinned), PageRequest.of(from / size, size));
        }

        return CompilationMapper.mapToLisCompilationDto(compilations);
    }

    @Override
    public CompilationDto getById(Long compId) {
        return CompilationMapper.mapToCompilationDto(
                repository.findById(compId).orElseThrow(() -> new ObjectNotFoundException("Compilation", compId)));
    }

}
