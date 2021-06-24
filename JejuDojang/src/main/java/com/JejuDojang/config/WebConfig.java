package com.JejuDojang.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.JejuDojang.config.auth.LoginUserArgumentResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration	// // 1개 이상 Bean을 등록하고 있음을 명시하는 어노테이션
public class WebConfig implements WebMvcConfigurer {

	private final LoginUserArgumentResolver loginUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// TODO Auto-generated method stub
		resolvers.add(loginUserArgumentResolver);
	}
	
	
}
