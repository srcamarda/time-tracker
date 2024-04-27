package com.dev.timetracker.utility;

import lombok.Getter;

@Getter
public class UniqueException extends RuntimeException {

    private final String field;

    public UniqueException(String message, String field) {
        super(message);
        this.field = field;
    }
}