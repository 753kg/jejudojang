package com.JejuDojang.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

@Builder
@Entity
@Table(name="ItineraryBoards")
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryBoard {

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO) //자동으로 생성
	   private Long bno; //bNo처럼 대문자가 섞이면 칼럼명에 언더바가 들어간다 --> Repository에서 변수이름 사용시 주의 (findBy대문자로 시작 + 변수이름은 그대로): findByRegDate
	   private String title;
	   private String writer;
	   private String content;
	   
	   @CreationTimestamp
	   private Timestamp regDate;
	   
	   @UpdateTimestamp
	   private Timestamp updateDate;
	   
	   //@JsonIgnore
	   @OneToMany(mappedBy = "itineraryBoard",cascade = CascadeType.ALL,fetch = FetchType.LAZY) 
	   private List<ItineraryBoardRepliesVO> replies;
	   
	   //mappedBy는 메여있다 .webBoardReply의 board속성이 ㅊ참조하고있다
	   //참조하고있어서 자유롭지못하다 자식이 있으면 부모는 지울수없다.
}
