package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.CustomDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitCreate {
    private String app;
    private String uri;
    private String ip;
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime timestamp;
}
