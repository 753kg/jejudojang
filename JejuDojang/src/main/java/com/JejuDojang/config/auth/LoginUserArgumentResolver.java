package com.JejuDojang.config.auth;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.JejuDojang.config.auth.dto.SessionUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver{
	
	private final HttpSession httpSession;
	
	// @LoginUser 어노테이션이 붙어 있고 파라미터 클래스 타입이 SessionUser.class 인 경우 true
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserclass = SessionUser.class.equals(parameter.getParameterType());
		return isLoginUserAnnotation && isUserclass;
	}

	// 파라미터에 전달할 객체 생성
	// 세션에서 객체 가져옴
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		return httpSession.getAttribute("user");
	}

	
}
