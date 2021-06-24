package com.JejuDojang.model;

import java.io.Serializable;



import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FriendsVOkey implements Serializable {

	MembersVO member;

	MembersVO friend;
}
