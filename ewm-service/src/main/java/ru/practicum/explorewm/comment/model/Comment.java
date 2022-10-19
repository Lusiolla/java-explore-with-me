package ru.practicum.explorewm.comment.model;

import lombok.*;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column(name = "event_id", nullable = false)
    private Long eventId;
    @Column(length = 7000, nullable = false)
    private String text;
    @Column(name = "created", nullable = false)
    LocalDateTime createdOn;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(name = "published")
    LocalDateTime publishedOn;
}
