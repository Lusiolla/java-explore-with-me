package ru.practicum.explorewm.statistics.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.CustomDateTimeSerializer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    private String app = "ewm-main-service";
    private String uri;
    private String ip;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime timestamp;

    public static EndpointHit mapToEndpointHit(HttpServletRequest request) {
        EndpointHit hit = new EndpointHit();
        hit.setUri(request.getRequestURI());
        hit.setIp(request.getRemoteAddr());
        hit.setTimestamp(LocalDateTime.now());
        return hit;
    }
}
