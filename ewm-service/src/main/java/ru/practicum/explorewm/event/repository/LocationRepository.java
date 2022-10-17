package ru.practicum.explorewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewm.event.location.model.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByLatAndLon(float lat, float lon);
}
