package com.JejuDojang.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LikeInfoDTO {

   long contentid;
   String addr1;
   String firstimage;
   String firstimage2;
   double mapx;
   double mapy;
   String tel;
   String title;
   String tag_name;
   String group_id;
   String email;
   
}