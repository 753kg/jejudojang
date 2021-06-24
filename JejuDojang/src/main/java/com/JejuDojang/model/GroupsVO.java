package com.JejuDojang.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
@Table(name="groups",uniqueConstraints = {@UniqueConstraint(name = "GROUP_ID_UNIQUE", columnNames = {"GROUP_ID"})})
@NoArgsConstructor
@AllArgsConstructor

public class GroupsVO {

	@Id
	private String group_id;

	private String group_name;
	
	private Date start_day;
	private Date end_day;
	
	
}
