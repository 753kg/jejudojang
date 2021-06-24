package com.JejuDojang.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 커스텀 어노테이션
@Target(ElementType.PARAMETER)	// 메소드의 파라미터로 선언된 객체에서만 사용하겠다
@Retention(RetentionPolicy.RUNTIME)	// 어느시점까지 어노테이션 메모리를 가져갈지 설정
public @interface LoginUser {	// @interface: 이 파일을 어노테이션 클래스로 지정

}
