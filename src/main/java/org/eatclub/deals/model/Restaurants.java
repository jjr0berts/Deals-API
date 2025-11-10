package org.eatclub.deals.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Restaurants {

    private List<Restaurant> restaurants;
}
