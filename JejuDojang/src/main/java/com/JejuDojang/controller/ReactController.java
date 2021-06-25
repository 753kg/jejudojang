package com.JejuDojang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.service.GroupService;
import com.JejuDojang.service.ItineraryService;
import com.JejuDojang.service.TourLikeService;


@Controller
@RequestMapping("/api")
public class ReactController {

	@Autowired
	TourLikeService tourLikesService;
	@Autowired
	ItineraryService itineraryService;
	@Autowired
	GroupService groupsService;
	
	@GetMapping("/itineraries")
	public String getTourLikesTitle(Model model,@RequestParam String group_id) {
		
	
		model.addAttribute("itinerary",itineraryService.selectItineraryAfterMap(group_id));
		
		model.addAttribute("days", groupsService.findGroupDays(group_id));
		model.addAttribute("groupid", group_id);
		return "itinerary/plan/fixingitineraries";
	}
	
	@GetMapping("/updateschedule")
	@ResponseBody
	public int updateSchedule(Double schedule,Long contentid,String group_id) {
		System.out.println("schedule : "+schedule);
		System.out.println("contentid : "+contentid);
		System.out.println("group_id : "+group_id);
		itineraryService.updateSchedule(schedule, contentid, group_id);
		return 0;
	}
	
	@GetMapping("/mypage")
	public String callmypage(@LoginUser SessionUser user,Model model) {
		List<GroupsVO> groups =  groupsService.selectGroupsbyemail(user.getEmail());
		model.addAttribute("groups",groups);
		System.out.println(groups);
		return "main/mypage";
	}
}

