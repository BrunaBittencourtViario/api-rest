package com.compassuol.apiproduto.config.exception;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status_code;
    private String message;

    public ExceptionResponse(Integer status_code, String message) {
        super();
        this.status_code = status_code;
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }


}
