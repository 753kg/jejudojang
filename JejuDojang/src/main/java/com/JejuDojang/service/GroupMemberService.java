package com.JejuDojang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.GroupMember;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.persistence.GroupMemberRepository;
import com.JejuDojang.persistence.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@Service
public class GroupMemberService {

	private final GroupMemberRepository gmRepo;
	private final GroupRepository groupRepo;
	
	
	public void save(GroupMember groupMember) {
		List<GroupMember> gm = gmRepo.findByGroupAndMember(groupMember.getGroup(), groupMember.getMember());
		if(gm.size() == 0) {
			gmRepo.save(groupMember);			
		}
	}
	
	public int getTotalGroupMemberCount(GroupsVO group) {
		return gmRepo.totalGroupMemberCount(group);
	}
}
