package ru.practicum.explorewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.event.dto.SortRequest;
import ru.practicum.explorewm.event.dto.EventFull;
import ru.practicum.explorewm.event.dto.EventShort;
import ru.practicum.explorewm.event.dto.PublicGetEventRequest;
import ru.practicum.explorewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class PublicEventController {

    private final EventService service;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("{id}")
    public EventFull getById(@NotNull @PathVariable Long id, HttpServletRequest request) {
        return service.getByIdPublic(id, request);
    }

    @GetMapping
    public Collection<EventShort> getAllByParam(@RequestParam(name = "text", required = false) String text,
                                                @RequestParam(name = "categories", required = false) Set<Long> categories,
                                                @RequestParam(name = "paid", required = false) Boolean paid,
                                                @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                                @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                                @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                                Boolean onlyAvailable,
                                                @RequestParam(name = "sort", required = false) SortRequest sort,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                                Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10")
                                                Integer size,
                                                HttpServletRequest request) {
        return service.getByParamPublic(new PublicGetEventRequest(
                text,
                categories,
                paid,
                LocalDateTime.from(formatter.parse(rangeStart)),
                LocalDateTime.from(formatter.parse(rangeEnd)),
                onlyAvailable,
                sort,
                from,
                size
        ), request);

    }

}
