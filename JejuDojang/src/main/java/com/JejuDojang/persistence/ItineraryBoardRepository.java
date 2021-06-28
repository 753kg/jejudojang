package com.JejuDojang.persistence;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.BoardVO;
import com.JejuDojang.model.ItineraryBoard;
import com.JejuDojang.model.QBoardVO;
import com.JejuDojang.model.QItineraryBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
public interface ItineraryBoardRepository extends CrudRepository<ItineraryBoard, Long>, QuerydslPredicateExecutor<ItineraryBoard> {

	public default Predicate makePredicate(String type, String keyword){
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QItineraryBoard board = QItineraryBoard.itineraryBoard;
		
		builder.and(board.bno.gt(0));
		
		if(type == null){
			return builder;
		}
		
		switch(type){
		case "t":
			builder.and(board.title.like("%" + keyword +"%"));
			break;
		case "c":
			builder.and(board.content.like("%" + keyword +"%"));
			break;
		case "w":
			builder.and(board.writer.like("%" + keyword +"%"));
			break;
		}
		
		return builder;
	}
	

}





