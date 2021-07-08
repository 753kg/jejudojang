package com.JejuDojang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;
import com.JejuDojang.model.GroupsVO;
import com.JejuDojang.model.ItineraryBoard;
import com.JejuDojang.model.MypageVO;
import com.JejuDojang.persistence.GroupRepository;
import com.JejuDojang.persistence.ItineraryBoardRepository;
import com.JejuDojang.service.GroupService;
import com.JejuDojang.service.ItineraryBoardService;
import com.JejuDojang.service.ItineraryService;
import com.JejuDojang.service.TourLikeService;
import com.JejuDojang.vo.PageMaker;
import com.JejuDojang.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/boards2/")
@Log
public class ItineraryBoardController {


	@Autowired
	ItineraryService itineraryService;
	@Autowired
	GroupService groupsService;
	@Autowired
	private ItineraryBoardService itineraryBoardService;

	@Autowired
	private GroupRepository groupRepo;

	@GetMapping("/shareItinerary")
	public String retrieveitinerary( String group_id, Model model) {
		log.info("shareItinerary groupid>>" + group_id);
		List<MypageVO> mypages = itineraryService.getMypageVO(group_id);
		System.out.println("mypages : "+mypages);
		model.addAttribute("mypages",mypages);
		model.addAttribute("days", groupsService.findGroupDays(group_id));
		return "main/retrieveitinerary2";
	}
	
	@GetMapping("/register")
	public void registerGET(@ModelAttribute("vo")ItineraryBoard vo, @LoginUser SessionUser user, Model model ){
		log.info("register get");
		List<GroupsVO> groupList =  groupRepo.selectGroupsbyemail(user.getEmail());
		model.addAttribute("groupList", groupList);
	}
	
	@PostMapping("/register")
	public String registerPOST(@ModelAttribute("vo")ItineraryBoard vo, RedirectAttributes rttr){
		
		log.info("register post");
		log.info("" + vo);

		itineraryBoardService.save(vo);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/boards2/list";
	}
	
	@GetMapping("/view")
	public void view(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
		
		log.info("BNO: "+ bno);
		
		itineraryBoardService.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
		
	}
		
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
		
		log.info("MODIFY BNO: "+ bno);
		
		itineraryBoardService.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
	}
	
	@PostMapping("/modify")
	public String modifyPost(ItineraryBoard board, PageVO vo, RedirectAttributes rttr ){
		
		log.info("Modify WebBoard: " + board);
		
		
		itineraryBoardService.findById(board.getBno()).ifPresent( origin ->{
		 
			origin.setTitle(board.getTitle());
			origin.setContent(board.getContent());
			
			itineraryBoardService.save(origin);
			rttr.addFlashAttribute("msg", "success");
			rttr.addAttribute("bno", origin.getBno());
		});
		
		//페이징과 검색했던 결과로 이동하는 경우 
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());

		return "redirect:/boards2/view";
	}
	
	
	@PostMapping("/delete")
	public String delete(Long bno, PageVO vo, RedirectAttributes rttr ){
		
		log.info("DELETE BNO: " + bno);
		
		itineraryBoardService.deleteById(bno);
		
		rttr.addFlashAttribute("msg", "success");

		//페이징과 검색했던 결과로 이동하는 경우 
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());

		return "redirect:/boards2/list";
	}
	/*
	
	@GetMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo, Model model){
		
		Pageable page = vo.makePageable(0, "bno");
		
		Page<WebBoard> result = repo.findAll(
				repo.makePredicate(vo.getType(), 
						           vo.getKeyword()), page);
		
		log.info(""+ page);
		log.info(""+result);
		
		log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());
		
		
		model.addAttribute("result", new PageMaker(result));
				
	}

}



*/
//조회(기본화면)	
@GetMapping("/list") 
public void list(@ModelAttribute("pageVO") PageVO vo, Model model//, Principal principal
){
	
	Pageable page = vo.makePageable(0, "bno");
	
	Page<ItineraryBoard> result = itineraryBoardService.findAll(
			itineraryBoardService.makePredicate(vo.getType(), vo.getKeyword()), page);
	
	log.info(""+ page);
	log.info(""+result);

	log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());
	
	model.addAttribute("result", new PageMaker(result));
	//System.out.println("로그인한사람1:" + principal);
		

  
  }
 


}