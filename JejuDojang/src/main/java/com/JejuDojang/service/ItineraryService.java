package com.JejuDojang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.persistence.ItineraryRepository;

@Service
public class ItineraryService {

	@Autowired
	ItineraryRepository repo;
	
	public List<JejuTourListVO> selectItineraryAfterMap(String group_id){
		return repo.selectItineraryAfterMap(group_id);
	}
	
	public int updateSchedule(Double schedule,Long itinerary_id) {
		return repo.updateSchedule(schedule, itinerary_id);
	}
	
}
