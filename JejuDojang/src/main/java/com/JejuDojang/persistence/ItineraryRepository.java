package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.JejuDojang.model.ItinerariesVO;
import com.JejuDojang.model.JejuTourListVO;

public interface ItineraryRepository extends CrudRepository<ItinerariesVO, Long> {
	
	@Query(value = "select jt "
			+ "from ItinerariesVO it "
			+ "left join JejuTourListVO jt "
			+ "on it.contentid = jt.contentid "
			+ "where it.group_id = ?1 ")
   public List<JejuTourListVO> selectedMarker(String groupId);

	@Modifying
	@Transactional
	@Query(value = "delete from ItinerariesVO "
			+ " where contentid = ?1 "
			+ " and group_id = ?2 " )
	public void deleteByContentid(Long contentid, String group_id);
	
	
	@Query(value="select jt \r\n"
			+ "from JejuTourListVO jt \r\n"
			+ "left join ItinerariesVO i \r\n"
			+ "on i.contentid = jt.contentid \r\n"
			+ "where i.group_id =?1")
	public List<JejuTourListVO> selectItineraryAfterMap(String group_id); 
	
	@Query("update ItinerariesVO set schedule = ?1 where  itinerary_id= ?2")
	public int updateSchedule(Double schedule,Long itinerary_id);

}