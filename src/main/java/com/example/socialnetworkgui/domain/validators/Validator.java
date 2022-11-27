package com.example.socialnetworkgui.domain.validators;

/**
 * Interface for implementing generic Validators
 * @param <T>, the type of the parameter we want to validate
 */

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}