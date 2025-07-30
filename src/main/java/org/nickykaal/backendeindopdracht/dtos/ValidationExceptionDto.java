package org.nickykaal.backendeindopdracht.dtos;

import org.nickykaal.backendeindopdracht.utils.ValidationResult;

import java.util.List;

public class ValidationExceptionDto {
    private final List<ValidationResult> errors;
    private static final String type = "Validation";

    public ValidationExceptionDto(List<ValidationResult> errors) {
        this.errors = errors;
    }
}
