package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.JejuDojang.model.TagsVO;

public interface TagRepository extends CrudRepository<TagsVO, String> {

	@Query("select distinct(t.tagName) from TagsVO t")
	public List<String> getTagNames();
	
	/*
	@Query("")
	public List<String> getTagsByGroupId(String groupid);
	
	select distinct al.tag_name
	from (select *
		  from (select j.contentid, j.addr1, j.firstimage, j.firstimage2, j.mapx, j.mapy, j.tel, j.title, t.tag_name
				from JejuTourListVO j join TagsVO t on j.cat3 = t.cat3) jt join tour_likes tl on jt.contentid = tl.tourlist_id) al
	where group_id = '1623911126616';
	*/
}
