package ru.prooftechit.smh.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prooftechit.smh.configuration.swagger.Tags;

@Controller
@Profile("dev")
@Tag(name = Tags.SWAGGER)
public class SwaggerController {

    @Operation(summary = "Редирект на swagger-ui", tags = Tags.SWAGGER)
    @RequestMapping("/docs/swagger")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
