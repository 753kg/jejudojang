package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.MembersVO;

public interface GroupRepository extends CrudRepository<GroupsVO, String>{

	@Query("select g from GroupsVO g where group_id = ?1 ")
	public GroupsVO selectByGroupID(String group_id);

	
	  @Query(value = "select * from groups g where group_id in (select group_id from groupmember g2 where email = ?1)"
	  ,nativeQuery = true) 
	  public List<GroupsVO> selectGroupsbyemail(String email);
	 
}
