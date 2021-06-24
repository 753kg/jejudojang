package com.JejuDojang.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.JejuTourListVO;

public interface JejuTourListRepository extends CrudRepository<JejuTourListVO, Long>{

	@Query("select j from JejuTourListVO j join TagsVO t on j.cat3 = t.cat3 "
			+ "where t.tagName = ?1 order by j.firstimage2 desc")
	public Page<JejuTourListVO> getPlacesByTag(String tag, Pageable paging);
	
}
