package com.JejuDojang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.ItineraryBoard;
import com.JejuDojang.model.ItineraryBoardRepliesVO;
import com.JejuDojang.persistence.ItineraryBoardRepliesRepository;

@Service
public class ItineraryBoardReplyService {

	@Autowired
	private ItineraryBoardRepliesRepository replyRepo;
	
	public void save(ItineraryBoardRepliesVO reply) {
		replyRepo.save(reply);
	}
	
	public List<ItineraryBoardRepliesVO> getRepliesOfItineraryBoard(ItineraryBoard board){
		return replyRepo.getRepliesOfItineraryBoard(board);
	}
	
	public void deleteById(Long rno) {
		replyRepo.deleteById(rno);
	}
	
	public Optional<ItineraryBoardRepliesVO> findById(Long rno) {
		return replyRepo.findById(rno);
	}
}
