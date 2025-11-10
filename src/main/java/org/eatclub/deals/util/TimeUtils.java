package org.eatclub.deals.util;

import lombok.extern.log4j.Log4j2;
import org.eatclub.deals.exception.BadRequestException;
import org.eatclub.deals.model.Deal;
import org.eatclub.deals.model.Restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
public class TimeUtils {

    public static LocalTime formatStringToLocalTime(String time){
        if (time == null || time.isBlank()) {
            log.warn("Time string is null or blank");
            return null;
        }

        //Sanitize input by removing spaces, dots, and standardizing AM/PM
        String standardizedTime = time.trim().toLowerCase()
                .replaceAll("\\s+", "")
                .replace(".", "")
                .replace("am", "AM")
                .replace("pm", "PM")
                .toUpperCase();

        DateTimeFormatter format12Hour = DateTimeFormatter.ofPattern("h:mma");
        DateTimeFormatter format24Hour = DateTimeFormatter.ofPattern("H:mm");

        try{
            return LocalTime.parse(standardizedTime, format12Hour);
        } catch (DateTimeParseException exception){
            try {
                return LocalTime.parse(standardizedTime, format24Hour);
            } catch (DateTimeParseException ex) {
                throw new BadRequestException("Invalid time format: " + time);
            }
        }
    }

    public static boolean isDealAvailable(Deal deal, Restaurant restaurant, LocalTime time){
        if (deal == null || time == null) {
            return false;
        }

        if(deal.getStart() != null && deal.getEnd() != null){
            LocalTime openTime = formatStringToLocalTime(deal.getStart());
            LocalTime closeTime = formatStringToLocalTime(deal.getEnd());
            return isTimeWithinRange(time, openTime, closeTime);
        } else if (restaurant!= null && restaurant.getOpen() != null && restaurant.getClose() != null){
            LocalTime openTime = formatStringToLocalTime(restaurant.getOpen());
            LocalTime closeTime = formatStringToLocalTime(restaurant.getClose());
            return isTimeWithinRange(time, openTime, closeTime);
        }
        // Fall back to false if no open/close and start/end times are specified
        return false;
    }

    private static boolean isTimeWithinRange(LocalTime time, LocalTime start, LocalTime end) {
        if(time == null || start == null || end == null){
            return false;
        }
        return !time.isBefore(start) && time.isBefore(end);
    }
}
