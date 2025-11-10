package org.eatclub.deals.client;

import org.eatclub.deals.model.Restaurant;
import org.eatclub.deals.model.Restaurants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RestaurantsClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;


    private RestaurantsClient restaurantsClient;

    private final String mockApiUrl = "https://mocked-api-url.com.au/restaurants";

    @BeforeEach
    public void init() {
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);

        restaurantsClient = new RestaurantsClient(restTemplateBuilder);

        ReflectionTestUtils.setField(restaurantsClient, "restaurantsApiUrl", mockApiUrl);
    }

    //@Disabled
    @Test
    public void getRestaurants_WithMockedResponse_ShouldBeSuccessful(){
        Restaurant restaurant = Restaurant.builder()
                .name("KFC")
                .build();
        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        ResponseEntity<Restaurants> responseEntity = ResponseEntity.ok(restaurants);

        Mockito.when(restTemplate.getForEntity(Mockito.eq(mockApiUrl), Mockito.eq(Restaurants.class)))
                .thenReturn(responseEntity);

        Restaurants response = restaurantsClient.getRestaurants();
        Assertions.assertNotNull(response.getRestaurants());
        Assertions.assertEquals(1, response.getRestaurants().size());
    }
}
