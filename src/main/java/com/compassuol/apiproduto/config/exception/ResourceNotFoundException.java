package com.compassuol.apiproduto.config.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {

        super("Not Found");
    }
}
