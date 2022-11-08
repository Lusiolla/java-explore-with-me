package ru.practicum.explorewm.event.model;

import lombok.*;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.comment.model.Comment;
import ru.practicum.explorewm.compilation.model.Compilation;
import ru.practicum.explorewm.event.location.model.Location;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(length = 7000)
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
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(length = 120)
    private String title;
    @Column
    private int views;
    @ManyToMany(mappedBy = "events")
    private Collection<Compilation> compilations;
    @OneToMany
    @JoinColumn(name = "event_id")
    private Collection<Comment> comments;
}
