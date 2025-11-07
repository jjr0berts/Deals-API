package org.eatclub.deals.util;

import org.eatclub.deals.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

public class TimeUtilsTest {

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
    public void formatStringToLocalTime_WithInvalidTime_ThrowsException(){
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            TimeUtils.formatStringToLocalTime("not a valid time");
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }
}
