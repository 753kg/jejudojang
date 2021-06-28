package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.BoardRepliesVO;
import com.JejuDojang.model.BoardVO;
import com.JejuDojang.model.ItineraryBoard;
import com.JejuDojang.model.ItineraryBoardRepliesVO;

public interface ItineraryBoardRepliesRepository extends CrudRepository<ItineraryBoardRepliesVO, Long> {

	@Query("SELECT r FROM ItineraryBoardRepliesVO r WHERE r.itineraryBoard = ?1 " +
		       " AND r.rno > 0 ORDER BY r.rno ASC")
		public List<ItineraryBoardRepliesVO> getRepliesOfItineraryBoard(ItineraryBoard board);

}
