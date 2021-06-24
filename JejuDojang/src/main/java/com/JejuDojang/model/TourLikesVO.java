package com.JejuDojang.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="tour_likes")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TourLikesVOkey.class)
public class TourLikesVO {

	@Id
	@ManyToOne
	@JoinColumn(name = "group_id")
	private GroupsVO group;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "email")
	private MembersVO member;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "tourlist_id")
	private JejuTourListVO tourlist;
}
