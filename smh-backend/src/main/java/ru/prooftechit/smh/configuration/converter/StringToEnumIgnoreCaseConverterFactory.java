package ru.prooftechit.smh.configuration.converter;

import java.util.Arrays;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

/**
 * @author Roman Zdoronok
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class StringToEnumIgnoreCaseConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumIgnoreCase(getEnumType(targetType));
    }

    private static class StringToEnumIgnoreCase<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnumIgnoreCase(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            String trimmedSource = source.trim();
            return Arrays.stream(this.enumType.getEnumConstants())
                         .filter(e -> e.name().equalsIgnoreCase(trimmedSource))
                         .findAny()
                         .orElse(null);
        }
    }

    public static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }
}
