package ru.practicum.explorewm.requests.model;

import lombok.*;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.requests.status.Status;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

}
