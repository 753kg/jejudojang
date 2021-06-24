package com.JejuDojang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.persistence.MemberRepository;

@Service
public class MemberService {

	@Autowired
	MemberRepository repo;
	
	public void save(MembersVO member) {
		repo.save(member);
	}
	
	public MembersVO findById(String email) {
		return repo.findById(email).get();
	}
	
	public List<MembersVO> getUsers(String keyword) {
		Pageable paging = PageRequest.of(0, 5);
		return repo.findByEmailContainingAndRole(keyword, MemberRole.USER, paging);
		//return repo.findByEmailContaining(keyword, paging);
	}
	
	public MembersVO makeGuestId() {
		String guestid = "guest" + System.currentTimeMillis();
		MembersVO guest = MembersVO.builder()
				.name("guest")
				.email(guestid)
				.role(MemberRole.GUEST)
				.build();
		repo.save(guest);
		return guest;
	}
}
