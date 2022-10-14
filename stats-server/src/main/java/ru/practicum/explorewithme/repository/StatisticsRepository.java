package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.dto.StatsCount;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatisticsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select new ru.practicum.explorewithme.dto.StatsCount(eh.app," +
            "count(distinct eh.ip)) " +
            "from EndpointHit as eh " +
            "where eh.uri = ?1 " +
            "group by eh.app")
    Collection<StatsCount> getUniqueViews(String uris);

    @Query(value = "select new ru.practicum.explorewithme.dto.StatsCount(eh.app, " +
            "count(distinct eh.ip)) " +
            "from EndpointHit as eh " +
            "where eh.uri = ?1 " +
            "and eh.timestamp between ?2 and ?3 " +
            "group by eh.app")
    Collection<StatsCount> getUniqueViews(String uris, LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.explorewithme.dto.StatsCount(eh.app, " +
            "count(eh.id)) " +
            "from EndpointHit as eh " +
            "where eh.uri = ?1 " +
            "and eh.timestamp between ?2 and ?3 " +
            "group by eh.app")
    Collection<StatsCount> getViews(String uris, LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.explorewithme.dto.StatsCount(eh.app," +
            "count(eh.id)) " +
            "from EndpointHit as eh " +
            "where eh.uri = ?1 " +
            "group by eh.app")
    Collection<StatsCount> getViews(String uris);
}
