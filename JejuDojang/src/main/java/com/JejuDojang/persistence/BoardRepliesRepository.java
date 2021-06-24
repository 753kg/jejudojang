package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.BoardRepliesVO;
import com.JejuDojang.model.BoardVO;

public interface BoardRepliesRepository extends CrudRepository<BoardRepliesVO, Long> {

	@Query("SELECT r FROM BoardRepliesVO r WHERE r.board = ?1 " +
		       " AND r.rno > 0 ORDER BY r.rno ASC")
		public List<BoardRepliesVO> getRepliesOfBoard(BoardVO board);

}
