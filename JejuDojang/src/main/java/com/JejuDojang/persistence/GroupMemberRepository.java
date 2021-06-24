package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.GroupMember;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.MembersVO;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long>{

	public List<GroupMember> findByGroupAndMember(GroupsVO group, MembersVO member);
}
