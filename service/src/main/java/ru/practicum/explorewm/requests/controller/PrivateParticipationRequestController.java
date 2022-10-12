package ru.practicum.explorewm.requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewm.requests.service.ParticipationRequestService;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Validated
public class PrivateParticipationRequestController {

    private final ParticipationRequestService service;

    @GetMapping
    public Collection<ParticipationRequestDto> getUserRequests(@NotNull @PathVariable Long userId) {

        return service.getUserRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto add(@NotNull @PathVariable Long userId,
                                       @NotNull @RequestParam Long eventId) {

        return service.add(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@NotNull @PathVariable Long userId,
                                          @NotNull @PathVariable Long requestId) {

        return service.cancel(userId, requestId);
    }

}
