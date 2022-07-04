package ru.prooftechit.smh.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Ошибки валидации.
 *
 * @author Aleksandr Kosich
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    /*
    * ОШИБКИ ДАННЫХ ФОРМ
    */

    /**
    * В строке должны быть только буквы
    */
    public static final String NOT_ONLY_LETTERS = "data-validation.not-only-letters";

    /**
     * Строка не должна быть пустой
     */
    public static final String EMPTY_FIELD = "data-validation.empty-field";

    /**
     * E-mail должен соответсвовать паттерну
     */
    public static final String EMAIL_NOT_VALID = "data-validation.email-not-valid";

    /**
     * E-mail должен быть уникальным
     */
    public static final String EMAIL_NOT_UNIQUE = "data-validation.email-not-unique";

    /**
     * Телефон должен быть уникальным
     */
    public static final String PHONE_NOT_UNIQUE = "data-validation.phone-not-unique";

    /**
     * Имя записи не уникально
     */
    public static final String NAME_NOT_UNIQUE = "data-validation.name-not-unique";

    /**
     * Телефон должен содержать 11 цифр
     */
    public static final String PHONE_NOT_VALID = "data-validation.phone-not-valid";

    /**
     * Пароль должен иметь не менее 8 символов, содержать буквы латинского алфавита и цифры. Также доступен ввод символов: `!@#$%^&*()_=+[]{};:"\|,.-<>/?'~
     */
    public static final String PASSWORD_INVALID_FORMAT = "data-validation.password-invalid-format";

    /**
     * Пароли не идентичны
     */
    public static final String PASSWORD_NOT_EQUALS = "data-validation.password-not-equals";

    /**
     * Неверно указан пароль
     */
    public static final String INVALID_PASSWORD = "data-validation.invalid-password";

    /**
     * Такого типа сервиса не существует
     */
    public static final String SERVICE_TYPE_NOT_EXIST = "data-validation.service-type-not-exist";

    /**
     * Такой группы документов не существует
     */
    public static final String DOCUMENT_GROUP_NOT_EXIST = "data-validation.document-group-not-exist";

    /**
     * Файл с указанным contentId не найден
     */
    public static final String FILE_NOT_EXIST = "validation.file-not-exist";

    /**
     * Файл не указан или пустой
     */
    public static final String FILE_EMPTY = "validation.file-empty";

    /**
     * Количество файлов должно быть до десяти
     */
    public static final String FILES_COUNT_MAX_10 = "data-validation.files-count-max-10";

    /**
     * Неподходящий тип файла
     */
    public static final String FILE_INVALID_TYPE = "validation.invalid-file-type";

    /**
     * Неоднозначный запрос. Непонятно чего хочет автор запроса
     */
    public static final String AMBIGUOUS_REQUEST = "validation.ambiguous-request";

    /**
     * Для выполнения операции над сущностью, она должна быть в указанном статусе
     */
    public static final String SHOULD_BE_IN_STATUS = "validation.entity-should-be-in-status";

    /**
     * Для выполнения операции над сущностью, она не должна быть в указанном статусе
     */
    public static final String SHOULD_NOT_BE_IN_STATUS = "validation.entity-should-not-be-in-status";

    /**
     * Некорректный формат данных
     */
    public static final String INVALID_FORMAT = "validation.invalid-format";


    /*
     * ОШИБКИ ДЕЙСТВИЙ ПОЛЬЗОВАТЕЛЯ
     */

    /**
     * Телефон или email не уникальны
     */
    public static final String PHONE_OR_EMAIL_NOT_UNIQUE = "behavior-validation.phone-or-email-not-unique";

    /**
     * Отсутствуют прилагаемые файлы
     */
    public static final String NO_ATTACHED_FILES = "behavior-validation.no-attached-files";

    /**
     * Ошибка сохранения файла
     */
    public static final String FAILED_TO_SAVE_A_FILE = "behavior-validation.failed-to-save-a-file";

    /**
     * Контент не найден
     */
    public static final String CONTENT_NOT_FOUND = "behavior-validation.content-not-found";

    /**
     * Контент более не доступен / удален
     */
    public static final String CONTENT_GONE = "behavior-validation.content-gone";

    /**
     * Пользователь не найден
     */
    public static final String USER_NOT_FOUND = "behavior-validation.user-not-found";

    /**
     * Запись не найдена
     */
    public static final String RECORD_NOT_FOUND = "behavior-validation.record-not-found";

    /**
     * Нет доступа к записи
     */
    public static final String RECORD_NOT_ACCESSIBLE = "behavior-validation.record-not-accessible";

    /**
     * Нельзя поместить файл в другой файл
     */
    public static final String INVALID_OPERATION_NESTED_FILE = "behavior-validation.parent-node-should-be-folder";

    /**
     * Файл не содержит список других файлов
     */
    public static final String INVALID_OPERATION_NOT_A_FOLDER = "behavior-validation.node-is-not-a-folder";

    /**
     * Уже есть файл с таким именем
     */
    public static final String INVALID_OPERATION_FILE_NAME_NOT_UNIQUE = "behavior-validation.file-name-is-not-unique";

    /**
     * Уже есть папка с таким именем
     */
    public static final String INVALID_OPERATION_FOLDER_NAME_NOT_UNIQUE = "behavior-validation.folder-name-is-not-unique";

    /**
     * Доступ к выбранному эндпоинту запрещен
     */
    public static final String FORBIDDEN = "behavior-validation.forbidden";

    /**
     * Недостаточно прав для выполнения данной операции
     */
    public static final String INSUFFICIENT_RIGHTS = "behavior-validation.insufficient-rights";

    /**
     * Запись используется для другой модели
     */
    public static final String RECORD_IN_USE = "behavior-validation.record-in-use";

    /**
     * Тип сервисной работы не найден
     */
    public static final String TYPE_NOT_FOUND = "behavior-validation.type-not-found";

    /**
     * Название и адрес объекта не уникальны
     */
    public static final String NAME_WITH_ADDRESS_NOT_UNIQUE = "behavior-validation.name-with-address-not-unique";

    /**
     * Модель и серийный номер не уникальны
     */
    public static final String MODEL_WITH_SERIAL_NUMBER_NOT_UNIQUE = "behavior-validation.model-with-serial-number-not-unique";

}
