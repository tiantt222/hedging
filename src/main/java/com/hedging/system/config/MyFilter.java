package com.hedging.system.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hedging.system.filter.AccessFilter;

@Configuration
public class MyFilter {

	@Bean
	public FilterRegistrationBean<Filter> sessionExpireFilter() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(this.AccessFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Bean
	public Filter AccessFilter() {
		return new AccessFilter();
	}

}
