package org.eatclub.deals.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorCode = HttpStatus.BAD_REQUEST.value();
        this.errorMessage = errorMessage;
    }
}
