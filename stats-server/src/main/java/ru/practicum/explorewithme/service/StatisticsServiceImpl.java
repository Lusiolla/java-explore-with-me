package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mapper.StatisticsMapper;
import ru.practicum.explorewithme.repository.StatisticsRepository;
import ru.practicum.explorewithme.dto.StatsCount;
import ru.practicum.explorewithme.dto.ViewStats;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository repository;

    @Override
    public EndpointHit addStatistics(EndpointHit endpointHit) {
        return repository.save(endpointHit);
    }

    @Override
    public Collection<ViewStats> getStatistics(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        Collection<ViewStats> stats = new ArrayList<>();
        for (String uri : uris) {
            Collection<StatsCount> statsCounts;
            if (unique) {
                if (start == null && end == null) {
                    statsCounts = repository.getUniqueViews(uri);
                } else {
                    statsCounts = repository.getUniqueViews(uri, start, end);
                }
            } else {
                if (start == null && end == null) {
                    statsCounts = repository.getViews(uri);
                } else {
                   statsCounts = repository.getViews(uri, start, end);
                }
            }
            for (StatsCount count : statsCounts) {
                stats.add(StatisticsMapper.mapToViewStats(count, uri));
            }
        }
        return stats;
    }
}
