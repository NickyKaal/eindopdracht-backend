package org.nickykaal.backendeindopdracht.utils;

public class ValidationResult {
    public final String field;
    public final String exception;

    public ValidationResult(String field, String exception) {
        this.field = field;
        this.exception = exception;
    }

    public static final String NOT_UNIQUE = "not unique";
    public static final String IN_USE = "in use";
    public static final String INVALID = "invalid";
}
