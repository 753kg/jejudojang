package com.JejuDojang.controller;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.service.GroupService;
import com.JejuDojang.service.MemberService;
import com.JejuDojang.service.TagService;
import com.JejuDojang.service.TourLikeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@Controller
public class StyleSelectController {
	
	private final TagService tagService;
	private final MemberService memberService;
	private final GroupService groupService;
	private final TourLikeService tourLikeService;
	private final ObjectMapper mapper;
	
	@GetMapping("/itinerary")
	public String with(Model model, @LoginUser SessionUser user) throws JsonProcessingException {
		String userinfo = mapper.writeValueAsString(user);
		model.addAttribute("userinfo", userinfo);
		return "/itinerary/with";
	}
	
	@GetMapping("/itinerary/findFriends/{keyword}")
	@ResponseBody
	public List<MembersVO> getUsers(@PathVariable String keyword) {
		return memberService.getUsers(keyword);
	}
	
	@GetMapping("/itinerary/makegroup")
	public String makeGroup(String itineraryName, String itineraryDate, String[] friendlist,
			@LoginUser SessionUser user, HttpSession httpSession, RedirectAttributes rttr) {
		
		String groupid = System.currentTimeMillis() + "";
		String[] dates = itineraryDate.split(" to ");
		Date start_day = null;
		Date end_day = null;
		if(!"".equals(dates[0])) {
			start_day = Date.valueOf(dates[0]);
			end_day = Date.valueOf(dates[0]);
			if(dates.length == 2) {
				end_day = Date.valueOf(dates[1]);
			}
		}
		
		// 1. 세션유저가 null --> guest계정생성
		if(user == null) {
			MembersVO guest = memberService.makeGuestId();
			user = new SessionUser(guest);
			httpSession.setAttribute("user", user);
		}
		
		if(user != null) {
			// 1. 그룹 insert , groupmember에 본인 inert
			groupService.saveGroup(groupid, itineraryName ,user.getEmail(), start_day, end_day);
			
			// 2. USER권한일때 친구를 초대했으면 groupmember에 친구들 insert
			if(user.getRole() == MemberRole.USER) {
				if(friendlist.length != 0) {
					groupService.inviteFriend(groupid, friendlist);
				}
			}
		}
		
		rttr.addAttribute("groupid", groupid);
		return "redirect:/itinerary/style";
	}
	
	@GetMapping("/itinerary/style")
	public void selectStyle(Model model, @RequestParam String groupid) {
		log.info("style>> groupid: " + groupid);
		
		List<String> tags = tagService.getTagNames();
		model.addAttribute("groupid", groupid);
		model.addAttribute("tags", tags);
	}
	
	@PostMapping("/itinerary/myfavorite")
	public void selectFavoritePlace(Model model, HttpServletRequest request, String groupid) {
		log.info(groupid);
		String[] tags = request.getParameterValues("tag");
		model.addAttribute("groupid", groupid);
		model.addAttribute("tags", tags);
	}
	
	@GetMapping("/itinerary/ourfavorite")
	public void retrieveOurFavorite(Model model, @RequestParam String groupid) {
		model.addAttribute("groupid", groupid);
		model.addAttribute("tags", tourLikeService.getTagsByGroupId(groupid));
	}
	
}
