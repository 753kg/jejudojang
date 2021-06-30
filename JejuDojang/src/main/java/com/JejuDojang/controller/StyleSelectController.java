package com.JejuDojang.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.service.GroupMemberService;
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
	private final GroupMemberService gmService;
	private final TourLikeService tourLikeService;
	private final ObjectMapper mapper;
	
	@GetMapping("/itinerary/with")
	public void with(Model model, @LoginUser SessionUser user) throws JsonProcessingException {
		String userinfo = mapper.writeValueAsString(user);
		model.addAttribute("userinfo", userinfo);
		//return "/itinerary/with";
	}
	
	@GetMapping("/itinerary/findFriends/{keyword}")
	@ResponseBody
	public List<MembersVO> getUsers(@PathVariable String keyword) {
		return memberService.getUsers(keyword);
	}
	@GetMapping("/itinerary/makegroup")
	public String makeGroup(String itineraryName, String itineraryDate, String[] friendlist,
			@LoginUser SessionUser user, HttpSession httpSession, RedirectAttributes rttr) throws MessagingException, IOException {
		
		String groupid = System.currentTimeMillis() + "";
		groupService.makeGroup(groupid, itineraryName, itineraryDate, friendlist, user);
		
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
		GroupsVO group = groupService.findById(groupid);
		model.addAttribute("groupid", groupid);
		model.addAttribute("tags", tourLikeService.getTagsByGroupId(groupid));
		model.addAttribute("totalGroupMemberCount", gmService.getTotalGroupMemberCount(group));
		model.addAttribute("currentGroupMemberCount", tourLikeService.getCurrentGroupMemberCount(group));
		
	}
	
}
