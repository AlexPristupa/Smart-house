package ru.prooftechit.smh.configuration.swagger;

import io.swagger.annotations.ApiParam;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Profile("dev")
public class SpringFoxConfig {
    public static final String AUTH = "Authorization";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .securityContexts(Collections.singletonList(securityContext()))
            .securitySchemes(Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
                                                           .name(AUTH)
                                                           .build()))
            .apiInfo(getApiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }


    private ApiInfo getApiInfo() {
        return ApiInfo.DEFAULT;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        return Collections.singletonList(new SecurityReference(AUTH, new AuthorizationScope[]{}));
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final SpringDataWebProperties webProperties) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return Collections.singletonList(
                    AlternateTypeRules.newRule(Pageable.class, pageableDocumentedType(webProperties.getPageable(), webProperties.getSort()))
                );
            }
        };
    }

    private Type pageableDocumentedType(SpringDataWebProperties.Pageable pageable, SpringDataWebProperties.Sort sort) {
        final String firstPage = pageable.isOneIndexedParameters() ? "1" : "0";
        return new AlternateTypeBuilder()
            .fullyQualifiedClassName(fullyQualifiedName(Pageable.class))
            .withProperties(Arrays.asList(
                property(pageable.getPageParameter(), Integer.class, Map.of(
                    "value", ((pageable.isOneIndexedParameters() ? "Номер" : "Индекс") + " страницы"),
                    "defaultValue", firstPage,
                    "allowableValues", String.format("range[%s, %s]", firstPage, Integer.MAX_VALUE),
                    "example", firstPage
                )),
                property(pageable.getSizeParameter(), Integer.class, Map.of(
                    "value", "Размер страницы",
                    "defaultValue", String.valueOf(pageable.getDefaultPageSize()),
                    "allowableValues", String.format("от 1 до %s", pageable.getMaxPageSize()),
                    "example", "20"
                )),
                property(sort.getSortParameter(), String[].class, Map.of(
                    "value", "Параметры сортировки записей результата: property(,asc|desc)"
                ))
            ))
            .build();
    }

    private String fullyQualifiedName(Class<?> convertedClass) {
        return String.format("%s.generated.%s", convertedClass.getPackage().getName(), convertedClass.getSimpleName());
    }

    private AlternateTypePropertyBuilder property(String name, Class<?> type, Map<String, Object> parameters) {
        return new AlternateTypePropertyBuilder()
            .name(name)
            .type(type)
            .canRead(true)
            .canWrite(true)
            .annotations(Collections.singletonList(AnnotationProxy.of(ApiParam.class, parameters)));
    }
}
