package com.JejuDojang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.ItinerariesVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.persistence.GroupRepository;
import com.JejuDojang.persistence.ItineraryRepository;
import com.JejuDojang.persistence.TagRepository;
import com.JejuDojang.persistence.TourLikeRepository;

@Controller
public class MapController {
	
	@Autowired
	TourLikeRepository tourLikesRepo;
		
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	ItineraryRepository itRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	//백업용
	@GetMapping("/test4")
	public String testCon3() {
		return "itinerary/map/test4";
	}

	@GetMapping("/selectedPlaces")
	public String selectedMapContent(@RequestParam String groupid, Model model) {
		model.addAttribute("groupid", groupid);
		System.out.println("groupid: " + groupid);
		model.addAttribute("selectedplaces", itRepo.selectedMarker(groupid));
		return "itinerary/map/selectedPlaces";
	}
	
	@GetMapping("/retrieveMarker")
	public String retrieveMarker(@RequestParam String groupid, Model model) {
		model.addAttribute("groupid", groupid);
		return "itinerary/map/retrieveMarker";
	}
	
	//좋아요 한것 마커 표시 
	@GetMapping("/itinerary/allMarker/{group_id}")
	@ResponseBody
	public List<JejuTourListVO> allMarker(@PathVariable String group_id ) {
		System.out.println("좋아요 마커 표시 group_id : "+group_id);
		GroupsVO g = groupRepo.findById(group_id).get();
		return tourLikesRepo.selectMapInfo(g);
	}
	//최종 일정 마커 표 시 
		@GetMapping("/itinerary/retrieveMarker/{group_id}")
		@ResponseBody
		public List<JejuTourListVO> retrieveMarker(@PathVariable String group_id){
			System.out.println("group_id :"+group_id);
			return itRepo.selectedMarker(group_id);
		}
	
	//고른 장소 DB에 insert시키기 
	@GetMapping("/insertPlaces/{group_id}")
	@ResponseBody
	public Boolean selectedPlaces(Long contentid, @PathVariable String group_id) {
		System.out.println("contentid : "+contentid);
		System.out.println("groupid : "+group_id);
		//중복 체크하기 
		Long a= (long) 0;
		ItinerariesVO itvo = new ItinerariesVO(a, group_id, contentid, 0.0);
		System.out.println(itRepo.checkDuplicated(contentid, group_id));
		if(itRepo.checkDuplicated(contentid, group_id).size() == 0) {
			System.out.println("null...중복이 아니니까 인서트");
			itRepo.save(itvo);
			return false;
		}else {
			return true;
		}
	}
	
	//좋아요 한것 삭제 하기
	@GetMapping("/deletePlaces")
	@ResponseBody
	public int deletePlaces(Long contentid, String group_id) {
		System.out.println("contentid : "+contentid+", group_id : "+group_id);
		int ret = 0;
		try {
			itRepo.deleteByContentid(contentid, group_id);
			ret = 1;
		}catch (Exception e) {}
		return ret;
	}
}
