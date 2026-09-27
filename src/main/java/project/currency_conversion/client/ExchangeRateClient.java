package project.currency_conversion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import project.currency_conversion.dto.ExchangeRateApiResponse;
import org.springframework.cache.annotation.Cacheable;

@Component
public class ExchangeRateClient {

    private final WebClient webClient;
    private final String baseUrl = "https://openexchangerates.org/api";

    public ExchangeRateClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${openexchangerates.app-id}")
    private String appId;

    @Cacheable("exchangeRates")
    public ExchangeRateApiResponse getRates() {
        ExchangeRateApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest.json")
                        .queryParam("app_id", appId)
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRateApiResponse.class)
                .block();

        return response;
    }
}