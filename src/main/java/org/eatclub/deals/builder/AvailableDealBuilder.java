package org.eatclub.deals.builder;

import org.eatclub.deals.model.Deal;
import org.eatclub.deals.model.Restaurant;
import org.eatclub.deals.response.AvailableDeal;

public class AvailableDealBuilder {

    public static AvailableDeal buildAvailableDealFromRestaurantDeal(Restaurant restaurant, Deal deal){
        return AvailableDeal.builder()
                .restaurantObjectId(restaurant.getObjectId())
                .restaurantName(restaurant.getName())
                .restaurantAddress1(restaurant.getAddress())
                .restaurantSuburb(restaurant.getSuburb())
                .restaurantOpen(restaurant.getOpen())
                .restaurantClosed(restaurant.getClose())
                .dealObjectId(deal.getObjectId())
                .discount(deal.getDiscount())
                .dineIn(deal.getDineIn())
                .lightning(deal.getLightning())
                .qtyLeft(deal.getQtyLeft())
                .build();
    }
}
