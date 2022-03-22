package com.justyoga.util.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final AppStatusCode appStatusCode;

    public AppException(String message, AppStatusCode appStatusCode) {
        super(appStatusCode.getCode() + " - " + appStatusCode.getDesc() + " - " + message);
        this.appStatusCode = appStatusCode;
    }

    public AppException(AppStatusCode appStatusCode) {
        super(appStatusCode.getCode() + " - " + appStatusCode.getDesc());
        this.appStatusCode = appStatusCode;
    }
}
