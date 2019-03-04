package com.tmt.pos.mypos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.List;

//courtesy : https://github.com/spring-projects/spring-mvc-showcase/blob/master/src/main/java/org/springframework/samples/mvc/config/WebMvcConfig.java


// DispatcherServlet context: defines Spring MVC infrastructure
// and web application components

@Configuration
@ComponentScan(basePackages = "com.tmt.pos.mypos")
@EnableWebMvc
@EnableScheduling
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LocalContainerEntityManagerFactoryBean emf;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //   registry.addFormatterForFieldAnnotation(new MaskFormatAnnotationFormatterFactory());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        final PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
        //1-based page number indexes in the request parameters
        pageableArgumentResolver.setOneIndexedParameters(true);
        pageableArgumentResolver.setFallbackPageable(PageRequest.of(0, 100));

        argumentResolvers.add(pageableArgumentResolver);

    }

    // Handle HTTP GET requests for /resources/** by efficiently serving
    // static resources under ${webappRoot}/resources/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/").setViewName("home");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());


    }


    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {

        SessionFactory sf = emf.getObject().unwrap(SessionFactory.class);
        Hibernate5Module hibernate5Module = new Hibernate5Module((Mapping) sf, sf);
        hibernate5Module.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);


        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .modulesToInstall(hibernate5Module);

        MappingJackson2HttpMessageConverter messageConverter =
                new MappingJackson2HttpMessageConverter(b.build());


        //messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper pathHelper = new UrlPathHelper();
        pathHelper.setRemoveSemicolonContent(false); // For @MatrixVariable's
        configurer.setUrlPathHelper(pathHelper);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(3000);
        // configurer.registerCallableInterceptors(new TimeoutCallableProcessingInterceptor());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}
