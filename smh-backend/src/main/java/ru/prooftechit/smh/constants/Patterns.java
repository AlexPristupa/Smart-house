package ru.prooftechit.smh.constants;

/**
 * @author Roman Zdoronok
 */
public interface Patterns {
    String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9`!@#$%^&*()_=+\\[\\]{};:\"\\\\|,.\\-<>\\/?'~]{8,}$";
    String FIO_PATTERN = "^(\\p{L}+[\\s\\-]{0,1})*\\p{L}+$";
    String EMPTY_STRING_PATTERN = "^$";
    String PATRONYMIC_PATTERN = FIO_PATTERN + "|" + EMPTY_STRING_PATTERN;
}
