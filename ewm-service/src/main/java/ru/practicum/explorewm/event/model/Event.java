package ru.practicum.explorewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.compilation.model.Compilation;
import ru.practicum.explorewm.event.State;
import ru.practicum.explorewm.event.location.model.Location;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "confirmed_requests")
    private int confirmedRequests;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id ", nullable = false)
    private Location location;

    @Column
    private Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    private String title;

    @Column
    private int views;

   @ManyToMany(mappedBy = "events")
    private Collection<Compilation> compilations;

}
