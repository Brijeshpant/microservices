package com.brij.exceptions;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> fields;
}
