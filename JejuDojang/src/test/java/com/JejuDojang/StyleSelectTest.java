package com.JejuDojang;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.JejuDojang.model.GroupMember;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.model.LikeInfoDTO;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.model.TagsVO;
import com.JejuDojang.persistence.GroupMemberRepository;
import com.JejuDojang.persistence.GroupRepository;
import com.JejuDojang.persistence.JejuTourListRepository;
import com.JejuDojang.persistence.MemberRepository;
import com.JejuDojang.persistence.TagRepository;
import com.JejuDojang.persistence.TourLikeRepository;
import com.JejuDojang.service.GroupMemberService;

import lombok.extern.java.Log;

@Log
@SpringBootTest
public class StyleSelectTest {

	@Autowired
	JejuTourListRepository repo;
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	TourLikeRepository tourLikeRepo;
	
	@Autowired
	MemberRepository mrepo;
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	GroupMemberService groupMemberService;
	
	@Autowired
	GroupMemberRepository gmRepo;
	/*
	@Test
	public void howyoulikethat() {
		GroupsVO group = groupRepo.findById("1624867468479").get();
		int totalcount = gmRepo.totalGroupMemberCount(group);
		int count = tourLikeRepo.howManySelectedStyle(group);
		log.info("총 " + count + "/" + totalcount + " 명의 친구가 선택을 완료했습니다.");
	}
	*/
	/*
	@Test
	public void getMemberByPlace() {
		List<MembersVO> members = new ArrayList<>();
		tourLikeRepo.getMemberByPlace("1623911126616", 1620883L).forEach(obj -> {
			log.info(Arrays.toString(obj));
		});
	}
	*/
	
	/*
	@Test
	public void getMemberByGroupidAndTag2() {
		Pageable paging = PageRequest.of(0, 6);
		tourLikeRepo.getPlaceMemberLikedByTag("1623911126616", "바다", paging).forEach(place -> {
			log.info(place.toString());
		});
	}
	*/
	
	//@Test
	public void getMemberByGroupidAndTag() {
		tourLikeRepo.getMemberByGroupAndTag("1623911126616", "바다").forEach(member -> {
			//System.out.println(Arrays.toString(member));
		});
	}
	
	//@Test
	/*
	public void objToDTO() {
		List<LikeInfoDTO> likeinfolist = new ArrayList<>();
		tourLikeRepo.getLikeInfoByGroupId("1623911126616").forEach(obj -> {
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
		likeinfolist.forEach(l -> {
			log.info(l.toString());
		});
	}
	*/
	//@Test
	public void getTagsByGroupid() {
		tourLikeRepo.getLikeInfoByGroupId("1623911126616").forEach(likeinfo -> {
			System.out.println(Arrays.toString(likeinfo));
		});
	}
	
	
	//@Test
	public void saveGroup() {
		GroupsVO group = GroupsVO.builder().group_id("테스트그룹2").build();
		groupRepo.save(group);
		/*
		GroupMember gm = GroupMember.builder()
				.group(group)
				.member(mrepo.findById("hhlkcy@gmail.com").get())
				.start_day(Date.valueOf("2021-06-16"))
				.end_day(Date.valueOf("2021-06-20"))
				.build();
		groupMemberService.save(gm);
				*/
	}
	
	//@Test
	public void friends() {
		/*
		mrepo.findById("hhlkcy@gmail.com").ifPresent(member -> {
			frepo.getFriends(member).forEach(f -> {
				log.info(f.getEmail());
			});
		});
		*/
	}
	
	//@Test
	public void paging() {
		Pageable paging = PageRequest.of(6, 6);
		Page<JejuTourListVO> result = repo.getPlacesByTag("바다", paging);
		result.getContent().forEach(place -> {
			log.info(place.toString());
		});
		log.info("한페이지의사이즈" + result.getSize());
		log.info("전체페이지" + result.getTotalPages());
		log.info("총 몇개?" + result.getTotalElements());
		
	}
	
	// @Test
	public void getPlaces() {
		/*
		repo.getPlacesByTag("바다").forEach(place -> {
			log.info(place.toString());
		});
		*/
	}
	
	// @Test
	public void getTags() {
		tagRepo.getTagNames().forEach(tag -> {
			log.info(tag);
		});
	}
	
	// @Test
	public void test1() {
		List<String> taglist = new ArrayList<>();
		taglist.add("A02070200");
		taglist.add("A02070100");
		taglist.add("A01010100");
		taglist.add("A01010400");
		/*
		repo.findByCat3In(taglist).forEach(place -> {
			log.info(place.toString());
		});*/
	}
	
	//@Test
	public void insertTag() {
		/*A01010100,A01010200,A01010300,A01010400,A01010500,A01010600,A01010700
		String[] cats = {"A01010100","A01010200","A01010300","A01010400","A01010500","A01010600","A01010700"
				,"A01010800","A01010900","A01011000","A01011100","A01011200","A01011300","A01011400","A01011500"
				,"A01011600","A01011700","A01011800","A01011900","A01020100","A01020200"};
		for(String c : cats) {
			TagsVO tag = TagsVO.builder().cat3(c).tagName("자연").build();
			tagRepo.save(tag);
		}
		*/
		String[] cats = {"A01010100","A01010200","A01010300","A01010400","A01010500","A01010600","A01010700"};
		for(String c : cats) {
			TagsVO tag = TagsVO.builder().cat3(c).tagName("숲").build();
			tagRepo.save(tag);
		}
		
	}
}
