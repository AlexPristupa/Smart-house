package ru.prooftechit.smh.configuration.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.prooftechit.smh.api.dto.error.AccessDeniedReason;
import ru.prooftechit.smh.api.dto.error.ForbiddenErrorResponseDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.configuration.filter.RealIpFilter;
import ru.prooftechit.smh.configuration.properties.SecurityProperties;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher STREAM_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/content/**");

    public static String ROLE_PREFIX = "ROLE_";

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthEntryPoint authEntryPoint;
    private final SecurityProperties securityProperties;
    private final List<HttpMessageConverter<?>> messageConverters;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncodingStrength());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false) ;
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public AccessDeniedHandler accessDeniedHandler(List<HttpMessageConverter<?>> messageConverters) {

        HttpMessageConverter<Object> converter =
            messageConverters.stream()
                             .filter(httpMessageConverter -> httpMessageConverter.canWrite(ForbiddenErrorResponseDto.class, MediaType.APPLICATION_JSON))
                             .findFirst()
                             .map(HttpMessageConverter.class::cast)
                             .orElse(null);
        return (request, response, ex) -> {
            if(converter == null) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
            }
            else {
                ForbiddenErrorResponseDto dto = ForbiddenErrorResponseDto.builder()
                                                                         .message(ErrorMessages.FORBIDDEN)
                                                                         .reason(AccessDeniedReason.RESTRICTED_ACCESS)
                                                                         .build();
                ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
                converter.write(dto, MediaType.APPLICATION_JSON, outputMessage);
            }
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers(HttpMethod.OPTIONS, "/**")

           // allow anonymous resource requests
           .antMatchers(
               "/*",
               "/api/v1/registration/**",
               "/{x:^(?!api$).*$}/**"
           );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(authEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers(ApiPaths.AUTH_LOGIN).permitAll()
            .antMatchers(ApiPaths.AUTH_REFRESH).permitAll()

            .antMatchers(ApiPaths.USERS + "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ROOT.name())

            .antMatchers(HttpMethod.GET, ApiPaths.SERVICES_TYPES + "/**").hasAnyRole(UserRole.WRITER.name(), UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.POST, ApiPaths.SERVICES_TYPES + "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.PUT, ApiPaths.SERVICES_TYPES + "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.PATCH, ApiPaths.SERVICES_TYPES + "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.DELETE, ApiPaths.SERVICES_TYPES + "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ROOT.name())

            .antMatchers(ApiPaths.PROFILE).authenticated()

            .antMatchers(HttpMethod.POST).hasAnyRole(UserRole.WRITER.name(), UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.PUT).hasAnyRole(UserRole.WRITER.name(), UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.PATCH).hasAnyRole(UserRole.WRITER.name(), UserRole.ADMIN.name(), UserRole.ROOT.name())
            .antMatchers(HttpMethod.DELETE).hasAnyRole(UserRole.WRITER.name(), UserRole.ADMIN.name(), UserRole.ROOT.name())
            
            .antMatchers(ApiPaths.ROOT + "/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtTokenFilter(jwtTokenProvider,
                                                userDetailsService,
                                                STREAM_REQUEST_MATCHER),
                                                UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler(messageConverters));
    }

    @Bean
    public FilterRegistrationBean<RealIpFilter> realIpFilter() {
        FilterRegistrationBean<RealIpFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RealIpFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }


    public static String role(UserRole userRole) {
        return ROLE_PREFIX + userRole.name();
    }

}
