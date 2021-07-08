package com.JejuDojang.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.ItineraryBoard;
import com.JejuDojang.persistence.ItineraryBoardRepository;
import com.querydsl.core.types.Predicate;

@Service
public class ItineraryBoardService {

	@Autowired
	private ItineraryBoardRepository repo;
	
	public void save(ItineraryBoard vo) {
		repo.save(vo);
	}
	
	public Optional<ItineraryBoard> findById(Long bno) {
		return repo.findById(bno);
	}
	
	public void deleteById(Long bno) {
		repo.deleteById(bno);
	}
	
	public Page<ItineraryBoard> findAll(Predicate predicate, Pageable pageable) {
		return repo.findAll(predicate, pageable);
	}
	
	public Predicate makePredicate(String type, String keyword) {
		return repo.makePredicate(type, keyword);
	}
}
