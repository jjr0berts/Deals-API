package org.eatclub.deals.builder;

import org.eatclub.deals.model.Deal;
import org.eatclub.deals.model.Restaurant;
import org.eatclub.deals.response.AvailableDeal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AvailableDealBuilderTest {

    @Test
    public void buildAvailableDealFromRestaurantDeal_WithValidRestaurantAndDeal_ShouldReturnAvailableDeal() {
        Restaurant restaurant = Restaurant.builder()
                .objectId("R123")
                .name("Test Restaurant")
                .address("123 Test St")
                .cuisines(List.of("Asian", "Japanese"))
                .imageLink("http://example.com/image.jpg")
                .suburb("Test Suburb")
                .open("9:00am")
                .close("9:00pm")
                .build();

        Deal deal = Deal.builder()
                .objectId("D456")
                .discount("20")
                .dineIn("true")
                .lightning("false")
                .qtyLeft("10")
                .build();

        AvailableDeal availableDeal = AvailableDealBuilder.buildAvailableDealFromRestaurantDeal(restaurant, deal);

        Assertions.assertNotNull(availableDeal);
        Assertions.assertEquals("R123", availableDeal.getRestaurantObjectId());
        Assertions.assertEquals("Test Restaurant", availableDeal.getRestaurantName());
        Assertions.assertEquals("123 Test St", availableDeal.getRestaurantAddress1());
        Assertions.assertEquals("Test Suburb", availableDeal.getRestaurantSuburb());
        Assertions.assertEquals("9:00am", availableDeal.getRestaurantOpen());
        Assertions.assertEquals("9:00pm", availableDeal.getRestaurantClosed());
        Assertions.assertEquals("D456", availableDeal.getDealObjectId());
        Assertions.assertEquals("20", availableDeal.getDiscount());
        Assertions.assertEquals("true", availableDeal.getDineIn());
        Assertions.assertEquals("false", availableDeal.getLightning());
        Assertions.assertEquals("10", availableDeal.getQtyLeft());
    }
}
