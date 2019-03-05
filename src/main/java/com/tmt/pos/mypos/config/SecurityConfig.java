package com.tmt.pos.mypos.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                /*   .httpBasic()
                   .and()
                   .authorizeRequests()
                   .antMatchers("/index.html", "/home", "/login").permitAll()
                   .anyRequest().authenticated();*/
                .authorizeRequests().anyRequest().authenticated().and()
                .formLogin().and()  //formLogin doesn't work for xhr requests
                //.httpBasic().and() //basic auth works for xhr requests
                .csrf().csrfTokenRepository(csrfTokenRepository());

    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }


    // CSRF protection. Here we only include the CsrfFilter instead of all of Spring Security.
    // See http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf
    // for more information on Spring Security's CSRF protection


    public CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookiePath("/"); //if this is run behind a proxy then cookie is set on the application context path, which deprives the client application from reading it. hence set the cookie on root path
        // cookieCsrfTokenRepository.setCookieHttpOnly(false);
        return cookieCsrfTokenRepository; //new CsrfFilter(cookieCsrfTokenRepository);//use of  HttpSessionCsrfTokenRepository is forbidden as we are not implemented any authentication mechanism yet
    }

    // Provides automatic CSRF token inclusion when using Spring MVC Form tags or Thymeleaf.
    // See http://localhost:8080/#forms and form.jsp for examples

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }
}
