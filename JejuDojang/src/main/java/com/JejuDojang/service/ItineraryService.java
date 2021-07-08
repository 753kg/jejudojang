package com.JejuDojang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.ItinerariesVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.model.MypageVO;
import com.JejuDojang.persistence.ItineraryRepository;

@Service
public class ItineraryService {

	@Autowired
	ItineraryRepository repo;
	
	public List<JejuTourListVO> selectItineraryAfterMap(String group_id){
		return repo.selectItineraryAfterMap(group_id);
	}
	
	public void updateSchedule(Double schedule,Long contentid,String group_id) {
		 repo.updateSchedule(schedule, contentid, group_id);
	}
	//select i.schedule,jt.firstimage,jt.title,i.group_id,jt.contentid
	public List<MypageVO> getMypageVO(String groupid){
		List<MypageVO> members = new ArrayList<>();
		System.out.println(repo.selectRetrieve(groupid));
		repo.selectRetrieve(groupid).forEach(obj -> {
			MypageVO m = MypageVO.builder()
					.schedule(String.valueOf(obj[0]))
					.firstimage(String.valueOf(obj[1]))
					.title(String.valueOf(obj[2]))
					.group_id(String.valueOf(obj[3]))
					.contentid(String.valueOf( obj[4]))
					.build();
					
			members.add(m);
			
			
		});
		return members;
	}
	
	public List<Object[]> selectRetrieve(String group_id){
		return repo.selectRetrieve(group_id);
	}

	public List<JejuTourListVO> selectedMarker(String groupid) {
		return repo.selectedMarker(groupid);
	}

	public void save(ItinerariesVO itvo) {
		repo.save(itvo);
	}

	public List<ItinerariesVO> checkDuplicated(Long contentid, String group_id) {
		return repo.checkDuplicated(contentid, group_id);
	}

	public void deleteByContentid(Long contentid, String group_id) {
		 repo.deleteByContentid(contentid, group_id);
	}

}