package org.eatclub.deals.response;

import lombok.Data;

import java.util.List;

@Data
public class AvailableDealsResponse {

    private List<AvailableDeal> availableDeals;

    //TODO: Builder for deals response
}
