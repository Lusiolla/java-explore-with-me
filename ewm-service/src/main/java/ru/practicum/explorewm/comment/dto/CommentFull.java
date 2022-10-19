package ru.practicum.explorewm.comment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.dataio.CustomDateTimeSerializer;
import ru.practicum.explorewm.event.state.State;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentFull {
    private long id;
    private String authorName;
    private long eventId;
    private String text;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    LocalDateTime createdOn;
    private State state;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    LocalDateTime publishedOn;
}
