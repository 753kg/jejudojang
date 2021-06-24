/**
 * 
 */


var noimageicon = "/images/icon/imageicon2.png";


function printPlaces(plist){
	$.each(plist, function(i, elt) {
		var $pitem = $("<div class='place-item'></div>");
		var tumbnailurl = elt["firstimage2"];
		if(tumbnailurl == ""){
			tumbnailurl = noimageicon;
		}
		var $ptitle = $("<div class='place-title'>" + elt["title"] + "</div>");
		var $pimg = $("<img class='img thumbnail' src='" + tumbnailurl + "'/>");	
		$pitem.append($ptitle);
		$pitem.append($pimg);
		
		$pitem.click(function(){
			printDetail(elt);
		});
		
		$(".placelist").append($pitem);
	});
}

function printDetailContent(elt){
	var placeIcon = "/images/icon/place.png";
	var telIcon = "/images/icon/telephone.png";
	var imgurl = elt.firstimage;
	if(imgurl == ""){
		imgurl = noimageicon;
	}
	
	var contentStr = "<img class='img-responsive' src='" + imgurl +"'/>";
	contentStr += "<p><img class='detail-icon' src='" + placeIcon + "'/>" + elt.addr1 + "</p>";
	if(elt.tel != "")
		contentStr += "<p><img class='detail-icon' src='" + telIcon + "'/>" + elt.tel + "</p>";
	
	$(".detail-content").append($(contentStr));
}

function printBlogSearch(searchQuery, display, start){
	var query = searchQuery.replace(/\//g, "");
	$.ajax({
		url: "naverBlog/" + query + "/" + display + "/" + start,
		async: false,
		success: function(responseData){
			var searchResult = JSON.parse(responseData);
			if(start == 1){
				blog_current_page = 1;
				blog_last_page = Math.ceil(searchResult.total / display);
				blog_query = query;
			}
			$.each(searchResult.items, function(i, e) {
				var str = "<div class='search-item'>";
				str += "<a href='" + e["link"] + "' target='_blank'>" + e["title"] + "</a>";
				str += "<p>" + e["description"] + "</p>";
				str += "</div>"
				var $div = $(str)
				$(".bloglist").append($div)
			});
		}
	});
}

function isLiked(groupid, contentid){
	var result = false;
	$.ajax({
		url: "isLiked/" + groupid + "/" + contentid,
		async: false,
		success: function(isliked){
			result = isliked;
		}
	});
	return result;
}

function likefunc(){
	var contentid = $(this).attr('cid');
	var imgtag = $(this).children("img");
	var cname = imgtag.attr('class');
	
	var likeStatus = "unliked";
	var likeIcon = "/images/icon/heart_before(1).png";
	var ajaxType = "delete";
	
	if(cname == 'unliked'){
		likeStatus = "liked";
		likeIcon = "/images/icon/heart_after.png"
		ajaxType = "post";
	}
	
	$.ajax({
		url: "/itinerary/like/" + groupid + "/" + contentid,
		type: ajaxType,
		success: function(){
			imgtag.attr('class', likeStatus);
			imgtag.attr('src', likeIcon);
		}
	});
} 

/* ourfavorite에서 재정의할 부분 */

function printDetailHeader(elt){
	var heartClass = "unliked";
	var heartImg = "/images/icon/heart_before(1).png";
	if(isLiked(groupid, elt.contentid)){
		heartClass = "liked";
		heartImg = "/images/icon/heart_after.png";
	}
	
	var headerStr = "<h1 class='detail-title'>" + elt.title + "</h1>";
	headerStr += "<div class='detail-heart' cid='" + elt.contentid + "'>";
	headerStr += "<img class='" + heartClass + "' src='" + heartImg + "'/>"
	headerStr += "</div>";
	
	$(".detail-header").append($(headerStr));
	
	$(".detail-heart").click(likefunc);
}


function getPlaces(tag, size, page){
	var plist;
	$.ajax({
		url: "/itinerary/getPlaces/" + tag + "/" + size + "/" + page,
		async: false,
		success: function(responseData){
			place_last_page = responseData.totalPages;
			//printPlaces(responseData.content);
			plist = responseData.content;
		}
	});
	return plist;
}

function printDetail(elt){
	$(".detail-header").children().remove();
	$(".detail-content").children().remove();
	$(".bloglist").children().remove();
	$(".placedetail").scrollTop(0);
	
	printDetailHeader(elt);
	
	printDetailContent(elt);
	
	// detail-search
	printBlogSearch(elt["title"], blog_display_data, 1);
	blog_observeLastChild(blog_io);
}