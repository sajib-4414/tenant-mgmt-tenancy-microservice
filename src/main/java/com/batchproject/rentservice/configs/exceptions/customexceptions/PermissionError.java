package com.batchproject.rentservice.configs.exceptions.customexceptions;

import lombok.Getter;

@Getter
public class PermissionError extends RuntimeException{
    private String code;
    public PermissionError(String message) {
        super(message);
        this.code = "item_permission_error";
    }

    public PermissionError(String code, String message) {
        super(message);
        this.code = code;
    }
    public PermissionError() {
        super("You dont have permission to do this action");
        this.code = "item_permission_error";
    }

}