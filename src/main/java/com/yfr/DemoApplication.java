package com.yfr;

import com.yfr.filter.Encoding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@ServletComponentScan
@SpringBootApplication
public class DemoApplication {
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		filterRegistrationBean.setFilter(this.encoding());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("encoding","utf-8");
		filterRegistrationBean.setName("Encoding");
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
    @Bean(name = "Encoding")
	public Encoding encoding(){
		return new Encoding();
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
