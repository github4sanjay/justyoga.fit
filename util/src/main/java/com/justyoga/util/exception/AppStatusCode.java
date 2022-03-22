package com.justyoga.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AppStatusCode {
    VALIDATION_FAILED("validation-failed", "Some validation failed", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(
            "internal-server-error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("not-found", "Resource not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST("invalid-request", "Invalid request", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("access-denied", "Access denied", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIAL("invalid-credential", "Credential is not valid", HttpStatus.UNAUTHORIZED),
    INVALID_AUTH_HEADER(
            "invalid-auth-header", "Invalid authentication header", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("invalid-token", "Invalid token", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("expired-token", "Token is expired", HttpStatus.UNAUTHORIZED),
    FILE_NOT_FOUND("file-not-found", "File not found", HttpStatus.NOT_FOUND),
    FILE_READ_FAILED(
            "file-read-failed",
            "Exception during reading a file",
            HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ID_TOKEN("invalid-id-tokem", "Invalid id token", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND("user-not-found", "User not found", HttpStatus.NOT_FOUND),
    TRAINER_NOT_FOUND("trainer-not-found", "Trainer not found", HttpStatus.NOT_FOUND),
    MEDIA_ERROR("media-error", "Error in media storage", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ERROR(
            "unauthorized", "Unauthorized to perform this action", HttpStatus.UNAUTHORIZED),
    INVALID_LOCATION_SEARCH(
            "invalid-location-search",
            "Invalid search, please search a valid location",
            HttpStatus.BAD_REQUEST),
    INVALID_LOCATION(
            "invalid-location",
            "Invalid location, please select a valid location",
            HttpStatus.BAD_REQUEST),
    ILLEGAL_ARGUMENT("illegal-argument", "Illegal argument passed", HttpStatus.BAD_REQUEST),
    ILLEGAL_STATE("illegal-state", "This should be application specific", HttpStatus.BAD_REQUEST),
    IP_LOCATION_API("location-api", "Ip location api failing", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST("user-exist", "User already exist", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String desc;
    private final HttpStatus httpStatus;
}
