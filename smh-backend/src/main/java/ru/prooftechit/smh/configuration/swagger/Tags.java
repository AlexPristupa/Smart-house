package ru.prooftechit.smh.configuration.swagger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Roman Zdoronok
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tags {
    public static final String AUTH = "Авторизация";
    public static final String REGISTER = "Регистрация";
    public static final String FACILITY = "Объект";
    public static final String SERVICE = "Сервис";
    public static final String HARDWARE = "Оборудование";
    public static final String DOCUMENT = "Документ";
    public static final String CONTENT = "Файл";
    public static final String SWAGGER = "Swagger";
    public static final String PROFILE = "Профиль";
    public static final String USER = "Пользователь";
    public static final String USER_EVENT = "Важные события";
}
