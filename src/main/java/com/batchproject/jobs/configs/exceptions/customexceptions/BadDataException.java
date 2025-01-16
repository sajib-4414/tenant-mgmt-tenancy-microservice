package com.batchproject.jobs.configs.exceptions.customexceptions;

import lombok.Getter;


@Getter
public class BadDataException extends RuntimeException{
    private String code;
    public BadDataException(String message) {
        super(message);
        this.code = "invalid_data";
    }

    public BadDataException(String code, String message) {
        super(message);
        this.code = code;
    }

}