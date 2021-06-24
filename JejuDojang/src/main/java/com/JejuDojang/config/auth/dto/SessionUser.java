package com.JejuDojang.config.auth.dto;

import java.io.Serializable;

import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionUser implements Serializable{
	// 인증된 사용자 정보 저장

	private String name;
	private String email;
	private String picture;
	private MemberRole role;
	
	public SessionUser(MembersVO user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.role = user.getRole();
	}
}
