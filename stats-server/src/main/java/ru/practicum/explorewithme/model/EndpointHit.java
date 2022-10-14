package ru.practicum.explorewithme.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.practicum.explorewithme.dataio.CustomDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ENDPOINT_HITS")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String app;
    @Column(length = 512)
    private String uri;
    @Column
    private String ip;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @Column
    private LocalDateTime timestamp;
}
