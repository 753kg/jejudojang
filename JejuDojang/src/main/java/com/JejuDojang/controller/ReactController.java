package com.JejuDojang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		System.out.println("controller : "+group_id);
		model.addAttribute("itinerary",itineraryService.selectItineraryAfterMap(group_id));
		
		model.addAttribute("days", groupsService.findGroupDays(group_id));
		model.addAttribute("groupid", group_id);
		return "itinerary/plan/fixingitineraries";
	}
	
	@PostMapping("/")
	public int updateSchedule() {
		return 1;
	}
}

