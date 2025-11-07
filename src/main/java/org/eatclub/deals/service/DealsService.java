package org.eatclub.deals.service;

import org.eatclub.deals.response.AvailableDealsResponse;
import org.springframework.stereotype.Service;

@Service
public class DealsService {

    public AvailableDealsResponse getAvailableDealsByTime(String timeOfDay){
        //TODO: Call API, format time, map response over time

        //TODO: If no timeOfDay, return all active deals
        return new AvailableDealsResponse();
    }
}
