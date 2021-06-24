package com.JejuDojang.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Builder
@Entity
@Table(name="friends")
@IdClass(FriendsVOkey.class)
public class FriendsVO {
 
	@Id
	@ManyToOne
	@JoinColumn(name = "member_email",insertable=false, updatable=false)
	private MembersVO member;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "friend_email",insertable=false, updatable=false)
	private MembersVO friend;
	
	
}
