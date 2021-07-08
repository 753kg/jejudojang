package com.JejuDojang.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.BoardVO;
import com.JejuDojang.persistence.BoardRepository;
import com.querydsl.core.types.Predicate;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository repo;
	
	public void save(BoardVO board) {
		repo.save(board);
	}
	
	public Optional<BoardVO> findById(Long bno) {
		return repo.findById(bno);
	}
	
	public void deleteById(Long bno) {
		repo.deleteById(bno);
	}
	
	public Predicate makePredicate(String type, String keyword) {
		return repo.makePredicate(type, keyword);
	}
	
	public Page<BoardVO> findAll(Predicate predicate, Pageable pageable) {
		return repo.findAll(predicate, pageable);
	}

}
