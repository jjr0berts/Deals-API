package org.eatclub.deals.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Deal {
    private String objectId;
    private String discount;
    private String dineIn;
    private String lightning;
    private String start;
    private String end;
    private String qtyLeft;
}
