package com.JejuDojang.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JejuDojang.model.BoardRepliesVO;
import com.JejuDojang.model.BoardVO;
import com.JejuDojang.persistence.BoardRepliesRepository;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/replies/*")
@Log
public class BoardRepliesController {

	@Autowired
	private BoardRepliesRepository replyRepo;
	
	
	/*
	 * public ResponseEntity<Void> addReply( //ResponseEntity 타입: 코드를 이용해서 직접 Http
	 * Response의 상태 코드와 데이터를 직접 제어해서 처리 가능
	 * 
	 * @PathVariable("bno")Long bno, //@PathVariable: URI의 일부를 파라미터로 받기 위해서 사용하는
	 * 어노테이션
	 * 
	 * @RequestBody BoardRepliesVO reply){ //@RequestBody: JSON으로 전달되는 데이터를 객체로 자동으로
	 * 변환하도록 처리
	 * 
	 * log.info("addReply............."); log.info("BNO: "+bno);
	 * log.info("REPLY: "+reply);
	 * 
	 * return new ResponseEntity<>(HttpStatus.CREATED); //상태코드 201을 의미하는 created라는
	 * 메시지를 전송하도록 함 }
	 */
	
	@Transactional
	@PostMapping("/{bno}")
	public ResponseEntity<List<BoardRepliesVO>> addReply(
			@PathVariable("bno")Long bno, 
			@RequestBody BoardRepliesVO reply){

		log.info("addReply..........................");
		log.info("BNO: " + bno);
		log.info("REPLY: " + reply);
		
		BoardVO board = new BoardVO();
		board.setBno(bno);
		
		reply.setBoard(board);
		
		replyRepo.save(reply);		
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
		
	}
	private List<BoardRepliesVO> getListByBoard(BoardVO board)throws RuntimeException{
		
		log.info("getListByBoard...." + board);
		return replyRepo.getRepliesOfBoard(board);
		
	}		
	
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<BoardRepliesVO>> remove(
			@PathVariable("bno")Long bno,
			@PathVariable("rno")Long rno){
		
		log.info("delete reply: "+ rno);
		
		replyRepo.deleteById(rno);
		
		BoardVO board = new BoardVO();
		board.setBno(bno);
		
		return  new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
		
	}
	
	
	@Transactional
	@PutMapping("/{bno}")
	public ResponseEntity<List<BoardRepliesVO>> modify(@PathVariable("bno")Long bno, 
			@RequestBody BoardRepliesVO reply){
	
		log.info("modify reply: "+ reply);
		
		replyRepo.findById(reply.getRno()).ifPresent(origin -> {
			
			origin.setReply(reply.getReply());
			
			replyRepo.save(origin);
		});
		
		BoardVO board = new BoardVO();
		board.setBno(bno);
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
	}
	


	@GetMapping("/{bno}")
	public ResponseEntity<List<BoardRepliesVO>> getReplies(
			@PathVariable("bno")Long bno){
	
		log.info("get All Replies..........................");
		
		BoardVO board = new BoardVO();
		board.setBno(bno);
		return new ResponseEntity<>(getListByBoard(board),HttpStatus.OK );
	}
	


}


