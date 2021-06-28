package com.JejuDojang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder
public class MypageVO {

	private String group_id; 
	private String contentid;
	private String schedule;
	private String firstimage;
	private String title;
	
}
