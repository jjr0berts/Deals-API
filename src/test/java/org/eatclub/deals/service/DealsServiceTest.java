package org.eatclub.deals.service;

import org.eatclub.deals.client.RestaurantsClient;
import org.eatclub.deals.model.Deal;
import org.eatclub.deals.model.Restaurant;
import org.eatclub.deals.model.Restaurants;
import org.eatclub.deals.response.AvailableDealsResponse;
import org.eatclub.deals.response.PeakDealsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.List;

public class DealsServiceTest {

    private RestaurantsClient restaurantsClient;
    private DealsService dealsService;

    @BeforeEach
    public void init() {
        restaurantsClient = Mockito.mock(RestaurantsClient.class);
        dealsService = new DealsService(restaurantsClient);
    }

    @Test
    public void getAvailableDealsByTime_WithValidTime_ShouldReturnDeals() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("3:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithValidTimeMultipleDeals_ShouldReturnDeals() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("9:00am")
                .end("5:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal, deal2))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("3:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithValidTimeMultipleDeals_ShouldReturnOneDealOnly() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("9:00am")
                .end("5:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal, deal2))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("9:00am");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithValidTimeMultipleRestaurantDeals_ShouldReturnDeals() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("9:00am")
                .end("5:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal, deal2))
                .build();

        Deal deal3 = Deal.builder()
                .start("11:00am")
                .end("4:00pm")
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Test Restaurant Number 2")
                .deals(List.of(deal3))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant, restaurant2))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("1:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(3, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithNoTimeMultipleRestaurantDeals_ShouldReturnAllDeals() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("9:00am")
                .end("5:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal, deal2))
                .build();

        Deal deal3 = Deal.builder()
                .start("11:00am")
                .end("4:00pm")
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Test Restaurant Number 2")
                .deals(List.of(deal3))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant, restaurant2))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime(null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(3, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithValidTimeNoAvailableDeals_ShouldReturnNoDeals() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("11:00am")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("3:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithNoRestaurants_ShouldReturnNoDeals() {
        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(null);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("3:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getAvailableDeals().size());
    }

    @Test
    public void getAvailableDealsByTime_WithNoRestaurantList_ShouldReturnNoDeals() {
        Restaurants restaurants = Restaurants.builder()
                .restaurants(null)
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        AvailableDealsResponse response = dealsService.getAvailableDealsByTime("3:00pm");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getAvailableDeals().size());
    }

    @Test
    public void getPeakDealTime_WithNoRestaurants_ShouldReturnNoPeakTime() {
        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(null);

        PeakDealsResponse response = dealsService.getPeakDealTime();
        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getPeakTimeStart());
        Assertions.assertNull(response.getPeakTimeEnd());
    }

    @Test
    public void getPeakDealTime_WithSingleRestaurant_ShouldReturnPeakTime() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("11:00am")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        PeakDealsResponse response = dealsService.getPeakDealTime();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getPeakTimeStart().equalsIgnoreCase(LocalTime.of(10,0).toString()));
        Assertions.assertTrue(response.getPeakTimeEnd().equalsIgnoreCase(LocalTime.of(11,0).toString()));
    }

    @Test
    public void getPeakDealTime_WithSingleRestaurantNoOpenCloseTime_ShouldReturnPeakTime() {
        Deal deal = Deal.builder()
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        PeakDealsResponse response = dealsService.getPeakDealTime();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getPeakTimeStart().equalsIgnoreCase(LocalTime.MIN.toString()));
        Assertions.assertTrue(response.getPeakTimeEnd().equalsIgnoreCase(LocalTime.MAX.toString()));
    }


    @Test
    public void getPeakDealTime_WithSingleRestaurantManyDeals_ShouldReturnPeakTime() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("12:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("11:00am")
                .end("12:00pm")
                .build();

        Deal deal3 = Deal.builder()
                .start("12:00pm")
                .end("3:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .deals(List.of(deal, deal2, deal3))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        PeakDealsResponse response = dealsService.getPeakDealTime();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getPeakTimeStart().equalsIgnoreCase(LocalTime.of(11,0).toString()));
        Assertions.assertTrue(response.getPeakTimeEnd().equalsIgnoreCase(LocalTime.of(12,0).toString()));
    }

    @Test
    public void getPeakDealTime_WithMuiltipleRestaurantsManyDeals_ShouldReturnPeakTime() {
        Deal deal = Deal.builder()
                .start("10:00am")
                .end("5:00pm")
                .build();

        Deal deal2 = Deal.builder()
                .start("1:00pm")
                .end("9:00pm")
                .build();

        Deal deal3 = Deal.builder()
                .start("6:00pm")
                .end("9:00pm")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Test Restaurant")
                .open("9:00am")
                .close("10:00pm")
                .deals(List.of(deal, deal2, deal3))
                .build();

        Deal deal4 = Deal.builder()
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Test Restaurant 2")
                .open("9:00am")
                .close("10:00pm")
                .deals(List.of(deal4))
                .build();

        Restaurants restaurants = Restaurants.builder()
                .restaurants(List.of(restaurant, restaurant2))
                .build();

        Mockito.when(restaurantsClient.getRestaurants()).thenReturn(restaurants);

        PeakDealsResponse response = dealsService.getPeakDealTime();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getPeakTimeStart().equalsIgnoreCase(LocalTime.of(13,0).toString()));
        Assertions.assertTrue(response.getPeakTimeEnd().equalsIgnoreCase(LocalTime.of(17,0).toString()));
    }
}
