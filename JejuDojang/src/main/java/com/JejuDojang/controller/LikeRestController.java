package com.JejuDojang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.JejuTourListVO;
import com.JejuDojang.model.LikeInfoDTO;
import com.JejuDojang.model.MembersVO;
import com.JejuDojang.model.TourLikesVO;
import com.JejuDojang.model.TourLikesVOkey;
import com.JejuDojang.service.GroupService;
import com.JejuDojang.service.JejuTourListService;
import com.JejuDojang.service.MemberService;
import com.JejuDojang.service.TourLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/itinerary/*")
public class LikeRestController {
	
	private final JejuTourListService jejuService;
	private final MemberService memberService;
	private final GroupService groupService;
	private final TourLikeService tourLikeService;
	
	@GetMapping("/getPlaces/{tag}/{size}/{page}")
	public Page<JejuTourListVO> getPlaces(@PathVariable String tag,
			@PathVariable int size, @PathVariable int page) {
		Pageable paging = PageRequest.of(page, size);
		Page<JejuTourListVO> result = jejuService.getPlacesByTag(tag, paging);
		return result;
	}
	
	@GetMapping("/isLiked/{groupid}/{contentid}")
	public boolean isLiked(@LoginUser SessionUser user, @PathVariable String groupid,
			@PathVariable Long contentid) {
		TourLikesVOkey tourLikeKey = new TourLikesVOkey(groupid, user.getEmail(), contentid);
		return tourLikeService.isLiked(tourLikeKey);
	}
	
	@PostMapping("/like/{groupid}/{contentid}")
	public TourLikesVO likeInsert(@LoginUser SessionUser user, @PathVariable String groupid,
			@PathVariable Long contentid) {
		MembersVO member = memberService.findById(user.getEmail());
		GroupsVO group = groupService.findById(groupid);
		JejuTourListVO place = jejuService.findById(contentid);
		TourLikesVO tourLike = new TourLikesVO(group, member, place);
		return tourLikeService.save(tourLike);
	}
	
	@DeleteMapping("/like/{groupid}/{contentid}")
	public void likeDelete(@LoginUser SessionUser user, @PathVariable String groupid,
			@PathVariable Long contentid) {
		MembersVO member = memberService.findById(user.getEmail());
		GroupsVO group = groupService.findById(groupid);
		JejuTourListVO place = jejuService.findById(contentid);
		TourLikesVO tourLike = new TourLikesVO(group, member, place);
		tourLikeService.delete(tourLike);
	}
	
	@GetMapping("/naverBlog/{query}/{display}/{start}")
	public String naverBlogSearch(@PathVariable String query, 
			@PathVariable int display, @PathVariable int start) {
		String clientId = "aJd23R7iUN13sjZfVCds"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "IsZoKMF4vv"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // json 결과
        apiURL += "&display=" + display;
        apiURL += "&start=" + start;
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        System.out.println(responseBody);
        return responseBody;
	}
	
	private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
    
    @GetMapping("/getMemberByTag/{groupid}/{tag}")
	public List<String> getMemberByTag(@PathVariable String groupid, @PathVariable String tag) {
		return tourLikeService.getMemberByGroupAndTag(groupid, tag);
	}
    
    @GetMapping("/getPlaceMemberLikedByTag/{groupid}/{tag}")
	public List<LikeInfoDTO> getPlaceMemberLikedByTag(@PathVariable String groupid, @PathVariable String tag) {
		return tourLikeService.getPlaceMemberLikedByTag(groupid, tag);
	}
    
    @GetMapping("/getMemberByPlace/{groupid}/{contentid}")
	public List<MembersVO> getMemberByPlace(@PathVariable String groupid, @PathVariable Long contentid) {
		return tourLikeService.getMemberByPlace(groupid, contentid);
	}
}
