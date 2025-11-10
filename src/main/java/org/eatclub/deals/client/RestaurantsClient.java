package org.eatclub.deals.client;

import lombok.extern.log4j.Log4j2;
import org.eatclub.deals.model.Restaurants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class RestaurantsClient {

    private final RestTemplate restTemplate;

    @Value("${api.restaurants.url}")
    private String restaurantsApiUrl;

    public RestaurantsClient(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

    public Restaurants getRestaurants() {
        log.info("Sending GET request to: {}", restaurantsApiUrl);

        ResponseEntity<Restaurants> response = restTemplate.getForEntity(restaurantsApiUrl, Restaurants.class);

        log.info("Received response with status: {}", response.getStatusCode());
        log.debug("Response body: {}", response.getBody());

        return response.getBody();
    }
}
