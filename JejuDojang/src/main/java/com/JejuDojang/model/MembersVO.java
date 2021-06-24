package com.JejuDojang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class MembersVO {
	
	@Id
	private String email;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String picture;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole role;
	
	public MembersVO update(String name, String picture) {
		this.name = name;
		this.picture = picture;
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
	
}
