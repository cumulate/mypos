package com.tmt.pos.mypos.config;


import org.springframework.web.filter.DelegatingFilterProxy;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;


public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ RootConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

   /* @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new DelegatingFilterProxy("csrfFilter")};
    }
*/
}
