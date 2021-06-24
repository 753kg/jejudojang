package com.JejuDojang.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.JejuDojang.model.BoardVO;
import com.JejuDojang.persistence.BoardRepository;
import com.JejuDojang.vo.PageMaker;
import com.JejuDojang.vo.PageVO;

import lombok.extern.java.Log;
import net.bytebuddy.build.HashCodeAndEqualsPlugin.ValueHandling.Sort;

@Controller
@RequestMapping("/boards/")
@Log
public class BoardController {


	@Autowired
	private BoardRepository repo;

	
	@GetMapping("/register")
	public void registerGET(@ModelAttribute("vo")BoardVO vo ){
		log.info("register get");
		//vo.setTitle("샘플 게시물 제목입니다....");
		//vo.setContent("내용을 처리해 봅니다 " );
		//vo.setWriter("user00");
	}
	
	@PostMapping("/register")
	public String registerPOST(@ModelAttribute("vo")BoardVO vo, RedirectAttributes rttr){
		
		log.info("register post");
		log.info("" + vo);

		repo.save(vo);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/boards/list";
	}
	
	@GetMapping("/view")
	public void view(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
		
		log.info("BNO: "+ bno);
		
		repo.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
		
	}
		
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
		
		log.info("MODIFY BNO: "+ bno);
		
		repo.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
	}
	
	@PostMapping("/modify")
	public String modifyPost(BoardVO board, PageVO vo, RedirectAttributes rttr ){
		
		log.info("Modify WebBoard: " + board);
		
		
		repo.findById(board.getBno()).ifPresent( origin ->{
		 
			origin.setTitle(board.getTitle());
			origin.setContent(board.getContent());
			
			repo.save(origin);
			rttr.addFlashAttribute("msg", "success");
			rttr.addAttribute("bno", origin.getBno());
		});
		
		//페이징과 검색했던 결과로 이동하는 경우 
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());

		return "redirect:/boards/view";
	}
	
	
	@PostMapping("/delete")
	public String delete(Long bno, PageVO vo, RedirectAttributes rttr ){
		
		log.info("DELETE BNO: " + bno);
		
		repo.deleteById(bno);
		
		rttr.addFlashAttribute("msg", "success");

		//페이징과 검색했던 결과로 이동하는 경우 
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());

		return "redirect:/boards/list";
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
	
	Page<BoardVO> result = repo.findAll(
	repo.makePredicate(vo.getType(), vo.getKeyword()), page);
	
	log.info(""+ page);
	log.info(""+result);

	log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());
	
	model.addAttribute("result", new PageMaker(result));
	//System.out.println("로그인한사람1:" + principal);
		

  
  }
 


}