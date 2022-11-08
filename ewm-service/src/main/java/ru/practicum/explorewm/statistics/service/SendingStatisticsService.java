package ru.practicum.explorewm.statistics.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewm.statistics.dto.EndpointHit;

@Service
public class SendingStatisticsService extends BasicStatisticsSender {

    private static final String API_PREFIX = "/hit";

    public SendingStatisticsService(
            @Value("http://stats-server:9090") String serverUrl,
            RestTemplateBuilder builder
    ) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void sendStatistics(EndpointHit endpointHit) {
        post("", endpointHit);
    }
}
