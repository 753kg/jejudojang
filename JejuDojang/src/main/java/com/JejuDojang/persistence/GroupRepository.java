package com.JejuDojang.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.GroupsVO;

public interface GroupRepository extends CrudRepository<GroupsVO, String>{

	@Query("select g from GroupsVO g where group_id = ?1 ")
	public GroupsVO selectByGroupID(String group_id);
}
