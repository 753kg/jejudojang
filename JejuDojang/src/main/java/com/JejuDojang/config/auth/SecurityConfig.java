package com.JejuDojang.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.JejuDojang.model.MemberRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity			// Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			// h2-console 화면 사용을 위해 해당 옵션 disable
		http
			.csrf().disable().headers().frameOptions().disable()
		
			.and()
			.authorizeRequests()	// URL별 권한관리 설정 시작
			.antMatchers("/login").permitAll()
			.antMatchers("/", "/css/**", "/img/**", "/js/**").permitAll()	// 전체열람
			//.antMatchers("/itinerary/**").permitAll()	// 전체열람
			.antMatchers("/styleSelect/**").hasRole(MemberRole.USER.name())	// USER 권한을 가진 사람만 가능
			.anyRequest().permitAll()
			//.anyRequest().authenticated()	// 나머지 URL은 인증된 사용자만 허용(로그인한 사용자)
			
			.and()
            .oauth2Login().loginPage("/login")
			
			.and()
			.logout().logoutSuccessUrl("/")	// 로그아웃 성공시 이동할 주소
			
			.and()			// oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
			.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
	}
	
}
