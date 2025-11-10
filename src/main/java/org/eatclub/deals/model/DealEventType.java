package org.eatclub.deals.model;

public enum DealEventType {
    START(1),
    END(-1);

    public final int value;

    DealEventType(int value) {
        this.value = value;
    }
}
