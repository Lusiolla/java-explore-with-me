package ru.practicum.explorewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewm.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long>, QuerydslPredicateExecutor<Compilation> {

    @Modifying
    @Query(value = "delete " +
            "from compilation_events as ce " +
            "where compilation_id = ?1 " +
            "and event_id = ?2", nativeQuery = true)
    void deleteEventFromCompilation(Long id, Long eventId);

    @Modifying
    @Query(value = "insert into " +
            "compilation_events " +
            "(event_id, compilation_id)" +
            "values (?1, ?2)", nativeQuery = true)
    void addEventToCompilation(Long id, Long eventId);

    @Modifying
    @Query(value = "update Compilation " +
            "set pinned = false " +
            "where id = ?1")
    void unpinCompilation(long compId);

    @Modifying
    @Query(value = "update Compilation " +
            "set pinned = true " +
            "where id = ?1")
    void pinCompilation(long compId);
}
