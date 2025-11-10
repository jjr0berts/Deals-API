package org.eatclub.deals.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeakDealsResponse {

    private String peakTimeStart;
    private String peakTimeEnd;
}
