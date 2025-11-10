package org.eatclub.deals.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AvailableDealsResponse {

    private List<AvailableDeal> availableDeals;
}
