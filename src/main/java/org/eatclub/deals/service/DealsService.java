package org.eatclub.deals.service;

import lombok.extern.log4j.Log4j2;
import org.eatclub.deals.builder.AvailableDealBuilder;
import org.eatclub.deals.client.RestaurantsClient;
import org.eatclub.deals.model.Restaurants;
import org.eatclub.deals.response.AvailableDeal;
import org.eatclub.deals.response.AvailableDealsResponse;
import org.eatclub.deals.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Log4j2
public class DealsService {

    private RestaurantsClient restaurantsClient;

    public DealsService (RestaurantsClient restaurantsClient){
        this.restaurantsClient = restaurantsClient;
    }

    public AvailableDealsResponse getAvailableDealsByTime(String timeOfDay){
        Restaurants restaurants = restaurantsClient.getRestaurants();
        if(restaurants == null || restaurants.getRestaurants() == null){
            log.warn("No restaurants found from Restaurants Service");
            return AvailableDealsResponse.builder()
                    .availableDeals(List.of())
                    .build();
        }

        LocalTime formattedTime = TimeUtils.formatStringToLocalTime(timeOfDay);

        // Filter deals based on the provided timeOfDay
        // If timeOfDay is null, include all deals
        List<AvailableDeal> availableDeals = restaurants.getRestaurants().stream()
                .flatMap(restaurant -> restaurant.getDeals().stream()
                        .filter(deal -> formattedTime == null || TimeUtils.isDealAvailable(deal, formattedTime))
                        .map(deal -> AvailableDealBuilder.buildAvailableDealFromRestaurantDeal(restaurant, deal))
                ).toList();

        return AvailableDealsResponse.builder()
                        .availableDeals(availableDeals)
                                .build();
    }
}
