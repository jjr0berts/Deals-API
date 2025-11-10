package org.eatclub.deals.util;

import org.eatclub.deals.exception.BadRequestException;
import org.eatclub.deals.model.Deal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

public class TimeUtilsTest {

    @Test
    public void formatStringToLocalTime_WithNullTime_NullReturned(){
        LocalTime time = TimeUtils.formatStringToLocalTime(null);
        Assertions.assertNull(time);
    }

    @Test
    public void formatStringToLocalTime_WithEmptyTime_NullReturned(){
        LocalTime time = TimeUtils.formatStringToLocalTime("    ");
        Assertions.assertNull(time);
    }

    @Test
    public void formatStringToLocalTime_WithValidTimeLowerCase_SuccessfullyParsed(){
        LocalTime time = TimeUtils.formatStringToLocalTime("3:00pm");
        Assertions.assertEquals(LocalTime.of(15, 0), time);
    }

    @Test
    public void formatStringToLocalTime_WithValidTimeUpperCase_SuccessfullyParsed(){
        LocalTime time = TimeUtils.formatStringToLocalTime("3:30PM");
        Assertions.assertEquals(LocalTime.of(15, 30), time);
    }

    @Test
    public void formatStringToLocalTime_WithValidTimeAMLowerCase_SuccessfullyParsed(){
        LocalTime time = TimeUtils.formatStringToLocalTime("9:45am");
        Assertions.assertEquals(LocalTime.of(9, 45), time);
    }

    @Test
    public void formatStringToLocalTime_WithValid24HourTime_SuccessfullyParsed(){
        LocalTime time = TimeUtils.formatStringToLocalTime("21:00");
        Assertions.assertEquals(LocalTime.of(21, 0), time);
    }

    @Test
    public void formatStringToLocalTime_WithInvalidTime_ThrowsException(){
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () ->
                TimeUtils.formatStringToLocalTime("not a valid time"));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @Test
    public void isDealAvailable_AvailableDeal_ReturnsTrue(){
        Deal deal = Deal.builder()
                .start("10:00AM")
                .end("11:00PM")
                .build();
        LocalTime time = LocalTime.of(15, 0);
        Assertions.assertTrue(TimeUtils.isDealAvailable(deal, time));
    }


    @Test
    public void isDealAvailable_AvailableDealNoTime_ReturnsTrue(){
        Deal deal = Deal.builder()
                .build();
        LocalTime time = LocalTime.of(15, 0);
        Assertions.assertTrue(TimeUtils.isDealAvailable(deal, time));
    }

    @Test
    public void isDealAvailable_AvailableDealAM_ReturnsFalse(){
        Deal deal = Deal.builder()
                .start("2:00pm")
                .end("9:00pm")
                .build();
        LocalTime time = LocalTime.of(10, 30);
        Assertions.assertFalse(TimeUtils.isDealAvailable(deal, time));
    }

    @Test
    public void isDealAvailable_UnavailableDeal_ReturnsFalse(){
        Deal deal = Deal.builder()
                .start("10:00AM")
                .end("5:00PM")
                .build();
        LocalTime time = LocalTime.of(18, 0);
        Assertions.assertFalse(TimeUtils.isDealAvailable(deal, time));
    }
}
