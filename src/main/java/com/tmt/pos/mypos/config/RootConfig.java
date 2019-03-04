package com.tmt.pos.mypos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

@Configuration
@ImportResource("classpath:applicationContext.xml") //just for fun, lets learn how to mixup things :-)
public class RootConfig {

    // CSRF protection. Here we only include the CsrfFilter instead of all of Spring Security.
    // See http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf
    // for more information on Spring Security's CSRF protection

    @Bean
    public CsrfFilter csrfFilter() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookiePath("/"); //if this is run behind a proxy then cookie is set on the application context path, which deprives the client application from reading it. hence set the cookie on root path
        // cookieCsrfTokenRepository.setCookieHttpOnly(false);
        return new CsrfFilter(cookieCsrfTokenRepository);//use of  HttpSessionCsrfTokenRepository is forbidden as we are not implemented any authentication mechanism yet
    }

    // Provides automatic CSRF token inclusion when using Spring MVC Form tags or Thymeleaf.
    // See http://localhost:8080/#forms and form.jsp for examples

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }
}