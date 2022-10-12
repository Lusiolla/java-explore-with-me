package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.StatisticsMapper;
import ru.practicum.explorewithme.dto.EndpointHitCreate;
import ru.practicum.explorewithme.dto.ViewStats;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.service.StatisticsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService service;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(path = "/hit")
    @PostMapping
    public EndpointHit addStatistics(@NotNull @RequestBody EndpointHitCreate hitCreate) {
        return service.addStatistics(StatisticsMapper.mapToEndpointHit(hitCreate));
    }

    @GetMapping("/stats")
    public Collection<ViewStats> getStatistics(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam Set<String> uris,
            @RequestParam Boolean unique) {
        return service.getStatistics(LocalDateTime.from(formatter.parse(start)),
                LocalDateTime.from(formatter.parse(end)), uris, unique);
    }
}
