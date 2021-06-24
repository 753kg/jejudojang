package com.JejuDojang.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
	
	// 스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 이 앞에 있어야한다.
	GUEST("ROLE_GUEST", "손님"),
	USER("ROLE_USER", "사용자");
	
	private final String key;
	private final String title;
}
