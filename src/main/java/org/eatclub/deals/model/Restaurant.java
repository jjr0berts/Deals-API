package org.eatclub.deals.model;

import lombok.Data;

import java.util.List;

@Data
public class Restaurant {
    private String objectId;
    private String name;
    private String address;
    private String suburb;
    private List<String> cuisines;
    private String imageLink;
    private String open;
    private String close;
    private List<Deal> deals;
}
