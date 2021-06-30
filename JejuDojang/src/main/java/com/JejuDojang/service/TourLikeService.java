package com.JejuDojang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.LikeInfoDTO;
import com.JejuDojang.model.MemberRole;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.model.TourLikesVO;
import com.JejuDojang.model.TourLikesVOkey;
import com.JejuDojang.persistence.TourLikeRepository;

@Service
public class TourLikeService {

	@Autowired
	TourLikeRepository repo;
	
	public boolean isLiked(TourLikesVOkey tourLikeKey) {
		return repo.existsById(tourLikeKey);
	}
	
	public TourLikesVO save(TourLikesVO tourLike) {
		return repo.save(tourLike);
	}
	
	public void delete(TourLikesVO tourLike) {
		repo.delete(tourLike);
	}
	
	public List<String> getTagsByGroupId(String groupid){
		return repo.getTagsByGroupId(groupid);
	}
	
	public List<String> getMemberByGroupAndTag(String groupid, String tag){
		return repo.getMemberByGroupAndTag(groupid, tag);
	}
	
	public List<LikeInfoDTO> getPlaceMemberLikedByTag(String groupid, String tag){
		return likeInfoObjToDTO2(repo.getPlaceMemberLikedByTag(groupid, tag));
	}
	
	public List<MembersVO> getMemberByPlace(String groupid, Long contentid){
		List<MembersVO> members = new ArrayList<>();
		repo.getMemberByPlace(groupid, contentid).forEach(obj -> {
			MembersVO m = MembersVO.builder()
					.email(String.valueOf(obj[0]))
					.name(String.valueOf(obj[1]))
					.picture(String.valueOf(obj[2]))
					.role(MemberRole.valueOf(String.valueOf(obj[3])))
					.build();
			
			members.add(m);
		});
		return members;
	}
	
	
	public List<LikeInfoDTO> likeInfoObjToDTO2(List<Object[]> objlist){
		List<LikeInfoDTO> likeinfolist = new ArrayList<>();
		objlist.forEach(obj -> {
			LikeInfoDTO likeinfo = LikeInfoDTO.builder()
					.contentid(Long.valueOf(String.valueOf(obj[0])))
					.title(String.valueOf(obj[1]))
					.addr1(String.valueOf(obj[2]))
					.firstimage(String.valueOf(obj[3]))
					.firstimage2(String.valueOf(obj[4]))
					.tag_name(String.valueOf(obj[5]))
					.tel(String.valueOf(obj[6]))
					.build();
			likeinfolist.add(likeinfo);
		});
		return likeinfolist;
	}
	
	
	public List<LikeInfoDTO> likeInfoObjToDTO(String groupid){
		// List<Object[]> -> List<LikeInfoDTO>
		List<LikeInfoDTO> likeinfolist = new ArrayList<>();
		repo.getLikeInfoByGroupId(groupid).forEach(obj -> {
			LikeInfoDTO likeinfo = LikeInfoDTO.builder()
					.contentid(Long.valueOf(String.valueOf(obj[0])))
					.addr1(String.valueOf(obj[1]))
					.firstimage(String.valueOf(obj[2]))
					.firstimage2(String.valueOf(obj[3]))
					.mapx(Double.valueOf(String.valueOf(obj[4])))
					.mapy(Double.valueOf(String.valueOf(obj[5])))
					.tel(String.valueOf(obj[6]))
					.title(String.valueOf(obj[7]))
					.tag_name(String.valueOf(obj[8]))
					.group_id(String.valueOf(obj[9]))
					.email(String.valueOf(obj[10]))
					.build();
			likeinfolist.add(likeinfo);
		});
		
		return likeinfolist;
	}
	
	public int getCurrentGroupMemberCount(GroupsVO group) {
		return repo.howManySelectedStyle(group);
	}
	
}
