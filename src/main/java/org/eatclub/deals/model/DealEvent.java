package org.eatclub.deals.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class DealEvent implements Comparable<DealEvent> {
    private LocalTime dealEventTime;
    private DealEventType dealEventType;


    @Override
    public int compareTo(DealEvent o) {
        return this.dealEventTime.compareTo(o.dealEventTime);
    }
}
