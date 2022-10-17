package ru.practicum.explorewm.compilation.mapper;

import ru.practicum.explorewm.compilation.dto.CompilationDto;
import ru.practicum.explorewm.compilation.dto.CompilationCreate;
import ru.practicum.explorewm.compilation.model.Compilation;
import ru.practicum.explorewm.event.mapper.EventMapper;
import ru.practicum.explorewm.event.model.Event;

import java.util.ArrayList;
import java.util.Collection;

public class CompilationMapper {

    public static CompilationDto mapToCompilationDto(Compilation compilation) {
        CompilationDto response = new CompilationDto();
        response.setId(compilation.getId());
        response.setPinned(compilation.getPinned());
        response.setTitle(compilation.getTitle());
        response.setEvents(EventMapper.mapToLisEventShort(compilation.getEvents()));
        return response;
    }

    public static Compilation mapToCompilation(CompilationCreate newCompilation, Collection<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilation.getPinned());
        compilation.setTitle(newCompilation.getTitle());
        compilation.setEvents(events);
        return compilation;
    }

    public static Collection<CompilationDto> mapToLisCompilationDto(Iterable<Compilation> compilations) {
        Collection<CompilationDto> response = new ArrayList<>();
        for (Compilation compilation : compilations) {
            response.add(mapToCompilationDto(compilation));
        }
        return response;
    }

}
