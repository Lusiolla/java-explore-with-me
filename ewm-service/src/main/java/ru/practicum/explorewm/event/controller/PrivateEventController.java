package ru.practicum.explorewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.event.EventMapper;
import ru.practicum.explorewm.event.dto.EventCreate;
import ru.practicum.explorewm.event.dto.EventFull;
import ru.practicum.explorewm.event.dto.EventShort;
import ru.practicum.explorewm.event.dto.EventUpdate;
import ru.practicum.explorewm.event.service.EventService;
import ru.practicum.explorewm.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewm.requests.service.ParticipationRequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final EventService service;

    private final ParticipationRequestService requestService;

    @GetMapping
    public Collection<EventShort> getUserEvents(@NotNull @PathVariable Long userId,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                               Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10")
                                               Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    @PatchMapping
    public EventFull update(@NotNull @PathVariable Long userId, @Valid @RequestBody EventUpdate updateEvent) {
        return service.userUpdate(userId, updateEvent);
    }

    @PostMapping
    public EventFull add(@NotNull @PathVariable Long userId, @Valid @RequestBody EventCreate event) {
        return service.add(userId, EventMapper.mapToEvent(event));
    }

    @GetMapping("{eventId}")
    public EventFull getByIdUserEvent(@NotNull @PathVariable Long userId,
                             @NotNull @PathVariable Long eventId)    {
        return service.getByIdUserEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventFull cancel(@NotNull @PathVariable Long userId,
                            @NotNull @PathVariable Long eventId) {

        return service.cancel(userId, eventId);
    }

    @GetMapping("{eventId}/requests")
    public Collection<ParticipationRequestDto> getUserRequest(@NotNull @PathVariable Long userId,
                                                  @NotNull @PathVariable Long eventId) {
        return requestService.getByEventIdUserRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipation(@NotNull @PathVariable Long userId,
                                                             @NotNull @PathVariable Long eventId,
                                                             @NotNull @PathVariable Long reqId) {
        return requestService.confirmParticipation(userId, eventId, reqId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipation(@NotNull @PathVariable Long userId,
                                                             @NotNull @PathVariable Long eventId,
                                                             @NotNull @PathVariable Long reqId) {
        return requestService.rejectParticipation(userId, eventId, reqId);
    }


}
