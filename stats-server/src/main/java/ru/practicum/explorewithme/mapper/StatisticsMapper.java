package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.EndpointHitCreate;
import ru.practicum.explorewithme.dto.StatsCount;
import ru.practicum.explorewithme.dto.ViewStats;
import ru.practicum.explorewithme.model.EndpointHit;

public class StatisticsMapper {
    public static EndpointHit mapToEndpointHit(EndpointHitCreate request) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(request.getApp());
        endpointHit.setUri(request.getUri());
        endpointHit.setIp(request.getIp());
        endpointHit.setTimestamp(request.getTimestamp());
        return endpointHit;
    }

    public static ViewStats mapToViewStats(StatsCount statsCount, String uri) {
        ViewStats stats = new ViewStats();
        stats.setApp(statsCount.getApp());
        stats.setUri(uri);
        stats.setHits(statsCount.getCount());
        return stats;
    }
}
