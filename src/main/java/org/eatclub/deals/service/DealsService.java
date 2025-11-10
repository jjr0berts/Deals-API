package org.eatclub.deals.service;

import lombok.extern.log4j.Log4j2;
import org.eatclub.deals.builder.AvailableDealBuilder;
import org.eatclub.deals.client.RestaurantsClient;
import org.eatclub.deals.model.*;
import org.eatclub.deals.response.AvailableDeal;
import org.eatclub.deals.response.AvailableDealsResponse;
import org.eatclub.deals.response.PeakDealsResponse;
import org.eatclub.deals.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class DealsService {

    private RestaurantsClient restaurantsClient;

    public DealsService(RestaurantsClient restaurantsClient) {
        this.restaurantsClient = restaurantsClient;
    }

    public AvailableDealsResponse getAvailableDealsByTime(String timeOfDay) {
        Restaurants restaurants = restaurantsClient.getRestaurants();
        if (restaurants == null || restaurants.getRestaurants() == null) {
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
                        .filter(deal -> formattedTime == null || TimeUtils.isDealAvailable(deal, restaurant, formattedTime))
                        .map(deal -> AvailableDealBuilder.buildAvailableDealFromRestaurantDeal(restaurant, deal))
                ).toList();

        return AvailableDealsResponse.builder()
                .availableDeals(availableDeals)
                .build();
    }

    public PeakDealsResponse getPeakDealTime() {
        Restaurants restaurants = restaurantsClient.getRestaurants();       //Deals = N
        if (restaurants == null || restaurants.getRestaurants() == null) {
            log.warn("No restaurants found from Restaurants Service");
            return PeakDealsResponse.builder()
                    .build();
        }

        List<DealEvent> dealEvents = new ArrayList<>();

        for (Restaurant restaurant : restaurants.getRestaurants()) {
            for (Deal deal : restaurant.getDeals()) {                   // O(N)
                LocalTime startTime = deal.getStart() != null ? TimeUtils.formatStringToLocalTime(deal.getStart()) : getRestaurantOpenTimeOrFallback(restaurant);
                LocalTime endTime = deal.getEnd() != null ? TimeUtils.formatStringToLocalTime(deal.getEnd()) : getRestaurantCloseTimeOrFallback(restaurant);

                dealEvents.add(DealEvent.builder().dealEventTime(startTime).dealEventType(DealEventType.START).build());
                dealEvents.add(DealEvent.builder().dealEventTime(endTime).dealEventType(DealEventType.END).build());
            }
        }

        Collections.sort(dealEvents);                                   // O(NlogN)

        int currentActiveDeals = 0;
        int maxDeals = 0;
        LocalTime peakTimeStart = null;
        LocalTime peakTimeEnd = null;
        boolean inPeakWindow = false;

        for (DealEvent dealEvent : dealEvents) {                        // O(N)
            currentActiveDeals += dealEvent.getDealEventType().value;
            if(currentActiveDeals > maxDeals) {
                maxDeals = currentActiveDeals;
                peakTimeStart = dealEvent.getDealEventTime();
                inPeakWindow = true;
            } else if (currentActiveDeals < maxDeals && inPeakWindow) {
                peakTimeEnd = dealEvent.getDealEventTime();
                inPeakWindow = false;
            }
        }

        return PeakDealsResponse.builder()
                .peakTimeStart(peakTimeStart != null ? peakTimeStart.toString() : null)
                .peakTimeEnd(peakTimeEnd != null ? peakTimeEnd.toString() : null)
                .build();
    }

    private LocalTime getRestaurantOpenTimeOrFallback(Restaurant restaurant){
        if(restaurant.getOpen() != null){
            return TimeUtils.formatStringToLocalTime(restaurant.getOpen());
        }
        // If there are no times specified, assume the restaurant is 24 hours
        return LocalTime.MIN;
    }

    private LocalTime getRestaurantCloseTimeOrFallback(Restaurant restaurant){
        if(restaurant.getClose() != null){
            return TimeUtils.formatStringToLocalTime(restaurant.getClose());
        }
        // If there are no times specified, assume the restaurant is 24 hours
        return LocalTime.MAX;
    }
}
