package com.JejuDojang.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;

public interface MemberRepository extends CrudRepository<MembersVO, String>{
	
	Optional<MembersVO> findByEmail(String email);	// 원래 있던 유저인지 신규 유저인지 판단 목적
	
	List<MembersVO> findByEmailContainingAndRole(String keyword, MemberRole role, Pageable paging);
	List<MembersVO> findByEmailContaining(String keyword, Pageable paging);

}
