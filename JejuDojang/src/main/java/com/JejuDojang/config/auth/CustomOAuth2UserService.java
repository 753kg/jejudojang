package com.JejuDojang.config.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.JejuDojang.config.auth.dto.OAuthAttributes;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	// 구글 로그인 이후 가져온 사용자의 정보 (email, name, picture) 들을 기반으로
	// 가입, 정보수정, 세선저장 등의 기능 지원
	
	private final MemberRepository userRepository;
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest
				.getClientRegistration()
				.getRegistrationId(); 		// 네이버로그인인지 구글로그인인지 구분
		String userNameAttributeName = userRequest
				.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUserNameAttributeName();
		
		// OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 클래스
		OAuthAttributes attributes = OAuthAttributes
				.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
				
		MembersVO user = saveOrUpdate(attributes);
		
		// SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO 클래스
		// User 클래스를 쓰지 않고 새로 만들어서 쓰는 이유 ???
		// 1. User 클래스에 직렬화를 구현하지 않음... 
		// 2. User 클래스가 엔티티이기때문 ....
		httpSession.setAttribute("user", new SessionUser(user));
		
		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), 
									attributes.getAttributes(), 
									attributes.getNameAttributeKey());
	}
	
	private MembersVO saveOrUpdate(OAuthAttributes attributes) {
		MembersVO user = userRepository.findByEmail(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
				.orElse(attributes.toEntity());
		return userRepository.save(user);
	}

}
