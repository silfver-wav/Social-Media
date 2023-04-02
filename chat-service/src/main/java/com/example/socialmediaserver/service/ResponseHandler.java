package com.example.socialmediaserver.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object data, HttpHeaders headers) {
        Map<String, Object> fieldMap = new HashMap<>();

        fieldMap.put("status", status.value());
        fieldMap.put("message", message);
        if (data != null)
            fieldMap.put("data", data);

        return ResponseEntity.status(status).headers(headers).body(fieldMap);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object data) {
        return generateResponse(status, message, data, null);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message) {
        return generateResponse(status, message, null);
    }
}
