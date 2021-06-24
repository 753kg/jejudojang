package com.JejuDojang.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="tags")
@NoArgsConstructor
@AllArgsConstructor
public class TagsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long tagId;
	
	String cat3;
	
	String tagName;
}
