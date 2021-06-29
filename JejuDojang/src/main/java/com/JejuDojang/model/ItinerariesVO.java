package com.JejuDojang.model;

import java.sql.Date;

import javax.persistence.Column;
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
@Table(name="itineraries")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItinerariesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itinerary_id;
	
	@Column(nullable = false)
	private String group_id;
	
	@Column(nullable = false)
	private Long contentid;
	
	private Double schedule;
	
	
	
}
