package com.tmt.pos.mypos.config;


import com.tmt.pos.mypos.config.auth.jwt.JwtAuthenticationEntryPoint;
import com.tmt.pos.mypos.config.auth.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /* @Resource(name = "userService")
     private UserDetailsService userDetailsService;
 */
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
        // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //.csrfTokenRepository(csrfTokenRepository()).and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/signup", "home/**", "static/**").permitAll()
                .antMatchers("/customer/salesMan*").permitAll() //work around for ngx type ahead module for the time being
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        //.formLogin().and()  //formLogin doesn't work for xhr requests
        //.httpBasic().and() //basic auth works for xhr requests
        ;

    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        PasswordEncoder encoder = encoder();
        User.UserBuilder builder = User.builder().passwordEncoder(encoder::encode);
        manager.createUser(builder.username("user").password("password").roles("USER").build());
        manager.createUser(builder.username("admin").password("admin").roles("ADMIN").build());
        return manager;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
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

}
