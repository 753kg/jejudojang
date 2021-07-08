package com.JejuDojang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.BoardRepliesVO;
import com.JejuDojang.model.BoardVO;
import com.JejuDojang.persistence.BoardRepliesRepository;

@Service
public class BoardReplyService {
	
	@Autowired
	private BoardRepliesRepository replyRepo;
	
	public void save(BoardRepliesVO reply) {
		replyRepo.save(reply);
	}
	
	public List<BoardRepliesVO> getRepliesOfBoard(BoardVO board){
		return replyRepo.getRepliesOfBoard(board);
	}
	
	public void deleteById(Long rno) {
		replyRepo.deleteById(rno);
	}
	
	public Optional<BoardRepliesVO> findById(Long rno) {
		return replyRepo.findById(rno);
	}

}
