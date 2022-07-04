package ru.prooftechit.smh.api.dto;

import ru.prooftechit.smh.validation.constraint.PasswordEquality;

/**
 * @author Roman Zdoronok
 */
@PasswordEquality
public interface PasswordHolder {
    String getPassword();
    String getPasswordRepeat();
}
