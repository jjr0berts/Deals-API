package org.eatclub.deals.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableDeal {
    private String restaurantObjectId;
    private String restaurantName;
    private String restaurantAddress1;
    private String restaurantSuburb;
    private String restaurantOpen;
    private String restaurantClosed;
    private String dealObjectId;
    private String discount;
    private String dineIn;
    private String lightning;
    private String qtyLeft;
}
