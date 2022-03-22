package com.justyoga.review.web.handler;

import com.justyoga.util.error.ErrorResponse;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequestMapping
@Slf4j
public class ErrorResponseAdvice {

    @ExceptionHandler(
            value = { // in priority order
                AppException.class,
                ConstraintViolationException.class,
                MethodArgumentNotValidException.class,
                Exception.class
            })
    protected ResponseEntity<Object> handleError(Exception ex, WebRequest request) {
        log.error("Exception occurred", ex);
        if (ex instanceof AppException) {
            var e = (AppException) ex;
            return new ResponseEntity<>(
                    new ErrorResponse(
                            Collections.singletonList(e.getAppStatusCode().getDesc()),
                            e.getAppStatusCode().getCode()),
                    e.getAppStatusCode().getHttpStatus());

        } else if (ex instanceof ConstraintViolationException) {

            var constraintViolations =
                    ((ConstraintViolationException) ex).getConstraintViolations();
            var msgs = new ArrayList<String>();
            constraintViolations.forEach(
                    constraintViolation -> msgs.add(constraintViolation.getMessage()));
            var errorDetails = new ErrorResponse(msgs, AppStatusCode.VALIDATION_FAILED.getCode());
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

        } else if (ex instanceof MethodArgumentNotValidException) {

            var methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            var errorDetails =
                    new ErrorResponse(
                            methodArgumentNotValidException
                                    .getBindingResult()
                                    .getFieldErrors()
                                    .stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .collect(Collectors.toList()),
                            AppStatusCode.VALIDATION_FAILED.getCode());

            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        Collections.singletonList(AppStatusCode.INTERNAL_ERROR.getDesc()),
                        AppStatusCode.INTERNAL_ERROR.getCode()),
                AppStatusCode.INTERNAL_ERROR.getHttpStatus());
    }
}
