package org.nickykaal.backendeindopdracht.exceptions;

import lombok.Getter;
import org.nickykaal.backendeindopdracht.utils.ValidationResult;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<ValidationResult> errors;

    public ValidationException(List<ValidationResult> errors) {
        super("Validation errors");

        this.errors = errors;
    }
}