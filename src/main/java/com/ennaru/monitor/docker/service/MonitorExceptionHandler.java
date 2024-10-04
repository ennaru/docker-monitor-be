package com.ennaru.monitor.docker.service;

import com.ennaru.monitor.docker.vo.DockerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.ennaru.monitor.docker"})
public class MonitorExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<DockerResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new DockerResponse("1000", e.getMessage(), null)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<DockerResponse> illegalStateExceptionHandler(IllegalStateException e) {
        return new ResponseEntity<>(
                new DockerResponse("1000", e.getMessage(), null)
                , HttpStatus.BAD_REQUEST);
    }

}
