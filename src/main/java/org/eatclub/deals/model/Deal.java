package org.eatclub.deals.model;

import lombok.Data;

@Data
public class Deal {
    private String objectId;
    private String discount;
    private String dineIn;
    private String lightning;
    private String open;
    private String close;
    private String qtyLeft;
}
