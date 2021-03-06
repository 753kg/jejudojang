package com.JejuDojang.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.GroupMember;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.persistence.GroupRepository;
import com.JejuDojang.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GroupService {
	
	private final MemberRepository memberRepo;
	private final GroupRepository groupRepo;
	private final GroupMemberService groupMemberService;
	private final MemberService memberService;
	private final HttpSession httpSession;
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	
	
	public GroupsVO findById(String groupid) {
		return groupRepo.findById(groupid).get();
	}
	
	public void saveGroup(String groupid, String groupName ,String userid, Date sday, Date eday) {
		GroupsVO group = GroupsVO.builder()
				.group_id(groupid)
				.group_name(groupName)
				.start_day(sday)
				.end_day(eday)
				.build();
		groupRepo.save(group);
		
		insertGroupMember(group, userid);
	}
	
	public void insertGroupMember(GroupsVO group, String userid) {
		GroupMember gm = GroupMember.builder()
				.group(group)
				.member(memberRepo.findById(userid).get())
				.build();
		groupMemberService.save(gm);
	}
	
	public void inviteFriend(String groupid, String[] friendlist) {
		GroupsVO group = groupRepo.findById(groupid).get();
		for(String fid : friendlist) {
			insertGroupMember(group, fid);			
		}
	}
	
	public long findGroupDays(String group_id) {
		
		GroupsVO groups = groupRepo.selectByGroupID(group_id);
		
		long diff = groups.getEnd_day().getTime() - groups.getStart_day().getTime();

		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

		return diffrence + 1;

	}
	
	public List<GroupsVO> selectGroupsbyemail(String email){
		System.out.println("service email : "+email);
		return groupRepo.selectGroupsbyemail(email);
	}
	
	public void makeGroup(String groupid, String itineraryName, String itineraryDate, 
			String[] friendlist, SessionUser user) {
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
		
		// 1. ??????????????? null --> guest????????????
		if(user == null) {
			MembersVO guest = memberService.makeGuestId();
			user = new SessionUser(guest);
			httpSession.setAttribute("user", user);
		}
		
		if(user != null) {
			String hostName = user.getName();
			// 1. ?????? insert , groupmember??? ?????? inert
			saveGroup(groupid, itineraryName ,user.getEmail(), start_day, end_day);
			
			// 2. USER???????????? ????????? ??????????????? groupmember??? ????????? insert
			if(user.getRole() == MemberRole.USER) {
				if(friendlist.length != 0) {
					inviteFriend(groupid, friendlist);
					sendMail(friendlist, groupid, hostName);
				}
			}
		}
	}
	
	public void sendMail(String[] friendEmail, String groupid, String hostName) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setSubject("[??????????????????] ????????? ????????? ????????? ??????????????? ");
			helper.setTo(friendEmail);
			
			Context context = new Context();
			context.setVariable("groupid", groupid);
			context.setVariable("hostName", hostName);
			
			String html = templateEngine.process("mail-template", context);
			helper.setText(html, true);
			javaMailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}

}
