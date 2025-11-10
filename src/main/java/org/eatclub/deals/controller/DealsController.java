package org.eatclub.deals.controller;

import org.eatclub.deals.response.AvailableDealsResponse;
import org.eatclub.deals.response.PeakDealsResponse;
import org.eatclub.deals.service.DealsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/deals")
public class DealsController {

    private final DealsService dealsService;

    public DealsController(DealsService dealsService){
        this.dealsService = dealsService;
    }

    @GetMapping
    public ResponseEntity<AvailableDealsResponse> getAvailableDeals(@RequestParam(required=false) String timeOfDay) {
        return ResponseEntity.ok(dealsService.getAvailableDealsByTime(timeOfDay));
    }


    @GetMapping("/peak-time")
    public ResponseEntity<PeakDealsResponse> getPeakDealTime() {
        return ResponseEntity.ok(dealsService.getPeakDealTime());
    }
}
