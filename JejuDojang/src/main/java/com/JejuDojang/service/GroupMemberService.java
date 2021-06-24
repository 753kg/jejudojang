package com.JejuDojang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.GroupMember;
import com.JejuDojang.persistence.GroupMemberRepository;

import lombok.extern.java.Log;

@Log
@Service
public class GroupMemberService {

	@Autowired
	GroupMemberRepository repo;
	
	public void save(GroupMember groupMember) {
		List<GroupMember> gm = repo.findByGroupAndMember(groupMember.getGroup(), groupMember.getMember());
		if(gm.size() == 0) {
			repo.save(groupMember);			
		}
	}
}
