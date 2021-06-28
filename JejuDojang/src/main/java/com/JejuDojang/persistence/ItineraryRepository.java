package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.JejuDojang.model.ItinerariesVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.model.MypageVO;

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
	
	@Modifying
	@Transactional
	@Query("update ItinerariesVO set schedule = ?1 where contentid= ?2 and group_id=?3")
	public void updateSchedule(Double schedule,Long contentid,String group_id);
	
	@Query(value = "select i.schedule,jt.firstimage,jt.title,i.group_id,jt.contentid from itineraries i join jeju_tourlists jt on i.contentid=jt.contentid where group_id = ?1",nativeQuery = true)
	public List<Object[]> selectRetrieve(String group_id); 

}
