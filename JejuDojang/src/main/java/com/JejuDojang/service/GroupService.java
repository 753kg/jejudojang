package com.JejuDojang.service;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.GroupMember;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.persistence.GroupRepository;
import com.JejuDojang.persistence.MemberRepository;

@Service
public class GroupService {
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	GroupMemberService groupMemberService;
	
	
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
		
		GroupMember gm = GroupMember.builder()
				.group(group)
				.member(memberRepo.findById(userid).get())
				.build();
		groupMemberService.save(gm);
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
		System.out.println("service id : "+group_id);
		GroupsVO groups = groupRepo.selectByGroupID(group_id);
		System.out.println("groups : "+groups);
		long diff = groups.getEnd_day().getTime() - groups.getStart_day().getTime();

		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

		return diffrence + 1;

	}

}
