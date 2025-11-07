package org.eatclub.deals.util;

import org.eatclub.deals.exception.BadRequestException;
import org.eatclub.deals.model.Deal;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {

    public static LocalTime formatStringToLocalTime(String time){
        String standardizedTime = time.toUpperCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        try{
            return LocalTime.parse(standardizedTime, formatter);
        } catch (DateTimeParseException exception){
            throw new BadRequestException("Invalid time format: " + time);
        }
    }

    //@TODO: Return if deal is available given time
    public static boolean isDealAvailable(Deal deal, LocalTime time){
        return true;
    }
}
