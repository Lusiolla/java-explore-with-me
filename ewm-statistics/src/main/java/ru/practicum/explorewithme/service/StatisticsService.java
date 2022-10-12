package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.ViewStats;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public interface StatisticsService {

    EndpointHit addStatistics(EndpointHit endpointHit);

    Collection<ViewStats> getStatistics(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique);
}
