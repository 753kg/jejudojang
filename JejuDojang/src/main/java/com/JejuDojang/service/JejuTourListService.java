package com.JejuDojang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.persistence.JejuTourListRepository;

@Service
public class JejuTourListService {

	@Autowired
	JejuTourListRepository repo;
	
	public Page<JejuTourListVO> getPlacesByTag(String tag, Pageable paging) {
		return repo.getPlacesByTag(tag, paging);
		
	}
	
	public JejuTourListVO findById(Long contentid) {
		return repo.findById(contentid).get();
	}
}
