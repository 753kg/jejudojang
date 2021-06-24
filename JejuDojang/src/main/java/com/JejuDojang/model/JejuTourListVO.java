package com.JejuDojang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "jeju_tourlists")
@NoArgsConstructor
@AllArgsConstructor
public class JejuTourListVO {

	@Id
	Long contentid;
	
	
	String addr1;
	String addr2;
	Long areacode;
	String cat1;
	String cat2;
	String cat3;
	Long contenttypeid;

	
	@Column(nullable = false)
	String firstimage;
	@Column(nullable = false)
	String firstimage2;
	
	@Column(nullable = false)
	double mapx;
	
	@Column(nullable = false)
	double mapy;
	

	String tel;
	
	@Column(nullable = false) 
	String title; 
	
	
}
