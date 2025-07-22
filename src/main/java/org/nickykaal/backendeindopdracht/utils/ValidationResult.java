package org.nickykaal.backendeindopdracht.utils;

public class ValidationResult {
    public String field;
    public String exception;

    public ValidationResult(String field, String exception) {
        this.field = field;
        this.exception = exception;
    }

    public static final String NOT_UNIQUE = "not unique";
    public static final String IN_USE = "in use";
}
