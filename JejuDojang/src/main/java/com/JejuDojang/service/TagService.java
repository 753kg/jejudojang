package com.JejuDojang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JejuDojang.model.TagsVO;
import com.JejuDojang.persistence.TagRepository;

@Service
public class TagService {

	@Autowired
	TagRepository repo;
	
	public List<String> getTagNames(){
		return (List<String>) repo.getTagNames();
	}
}
