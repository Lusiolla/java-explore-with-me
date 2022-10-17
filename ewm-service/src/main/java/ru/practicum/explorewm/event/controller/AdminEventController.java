package ru.practicum.explorewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.event.dto.*;
import ru.practicum.explorewm.event.service.EventService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {

    private final EventService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public Collection<EventFull> getByParam(@RequestParam(name = "users") Set<Long> users,
                                            @RequestParam(name = "states") Set<State> states,
                                            @RequestParam(name = "categories") Set<Long> categories,
                                            @RequestParam(name = "rangeStart") String rangeStart,
                                            @RequestParam(name = "rangeEnd") String rangeEnd,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                            Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10")
                                            Integer size) {
        return service.getByParamAdmin(
                new AdminGetEventRequest(users, states, categories, LocalDateTime.from(formatter.parse(rangeStart)),
                        LocalDateTime.from(formatter.parse(rangeEnd)), from, size)
        );
    }

    @PutMapping("{eventId}")
    public EventDto update(@NotNull @PathVariable Long eventId, @RequestBody AdminEventUpdate updateEvent) {
        return service.adminUpdate(eventId, updateEvent);
    }

    @PatchMapping("/{eventId}/publish")
    public EventPublished publish(@NotNull @PathVariable Long eventId) {
        return service.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto reject(@NotNull @PathVariable Long eventId) {
        return service.reject(eventId);
    }

}
