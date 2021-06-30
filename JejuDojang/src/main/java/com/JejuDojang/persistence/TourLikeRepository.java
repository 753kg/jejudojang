package com.JejuDojang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.model.TourLikesVO;
import com.JejuDojang.model.TourLikesVOkey;

public interface TourLikeRepository extends JpaRepository<TourLikesVO, TourLikesVOkey>{

	public boolean existsById(TourLikesVOkey tourLikeKey);
	
	// 그룹 멤버들이 선택한 tag들 가져오기
	@Query(value = "select distinct tag_name from likeinfo_view where group_id = ?1", nativeQuery = true)
	public List<String> getTagsByGroupId(String groupid);
	
	// ㅁㅁ태그를 선택한 멤버
	@Query(value = "select m.name from members m where m.email in "
			+ "(select distinct email from likeinfo_view where group_id = ?1 and tag_name = ?2)", nativeQuery = true)
	public List<String> getMemberByGroupAndTag(String groupid, String tag);
	
	// 태그별 멤버들이 좋아하는 장소
	@Query(value = "select distinct contentid, title, addr1, firstimage, firstimage2, tag_name, tel "
			+ "from likeinfo_view where group_id = ?1 and tag_name = ?2", nativeQuery = true)
	public List<Object[]> getPlaceMemberLikedByTag(String groupid, String tag);
	
	// 이 장소를 좋아하는 멤버
	@Query(value = "select m.* from members m where m.email in "
			+ "(select distinct email from likeinfo_view where group_id = ?1 and contentid = ?2)", nativeQuery = true)
	public List<Object[]> getMemberByPlace(String groupid, Long contentid);
	
	// 모든 좋아요 정보
	@Query(value = "select * from likeinfo_view where group_id = ?1", nativeQuery = true)
	public List<Object[]> getLikeInfoByGroupId(String groupid);
	
	 @Query(value = "select jt "
	         + "from TourLikesVO tl "
	         + "left join JejuTourListVO jt "
	         + "on tl.tourlist = jt.contentid "
	         + "where tl.group =?1 ")
	public List<JejuTourListVO> selectMapInfo(GroupsVO groupId);
	 
	// 몇명의 친구들이 스타일을 골랐는지
	 @Query("select count(distinct tl.member) from TourLikesVO tl where tl.group = ?1")
	 public int howManySelectedStyle(GroupsVO group);
}
