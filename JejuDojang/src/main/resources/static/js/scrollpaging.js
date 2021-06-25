/**
 * place & blog scroll paging 함수, 변수 정의
 */

var msg = "<div class='loading-msg animate__animated animate__fadeIn text-center'>";
msg += "<img src='/images/icon/circles-menu-1.gif' class='loading-icon'/>";
msg += "</div>";
$msg = $(msg);

var $lastmsg = $("<p>마지막 페이지입니다.</p>");

var blog_display_data = 5;
var blog_last_page, blog_query, blog_current_page;

var place_last_page, place_tag;
var place_display_data = 5;
var place_current_page = 0;

const observer_option = {
        root: null,
        rootMargin: "0px 0px 0px 0px",
        threshold: 0.5
};

const place_io = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {

        // entry.isIntersecting: 특정 요소가 뷰포트와 50%(threshold 0.5) 교차되었으면
        if (entry.isIntersecting) {
			
			
			$(".placelist").append($msg);
            // 다음 데이터 가져오기: 자연스러운 연출을 위해 setTimeout 사용
            setTimeout(() => {
            	var plist = getPlaces(place_tag, place_display_data, ++place_current_page);
            	printPlaces(plist);
            	observer.unobserve(entry.target);
                place_observeLastChild(observer);

				$msg.remove();
            }, 1000);
        }
    })
}, observer_option)

function place_observeLastChild(intersectionObserver) {

    const listChildren = document.querySelectorAll(".place-item");
    listChildren.forEach(el => {
        if (!el.nextSibling && place_current_page < place_last_page) {
            intersectionObserver.observe(el); // el에 대하여 관측 시작
        } else if (place_current_page >= place_last_page) {
            intersectionObserver.disconnect();
        }

    })
}

const blog_io = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {

        // entry.isIntersecting: 특정 요소가 뷰포트와 50%(threshold 0.5) 교차되었으면
        if (entry.isIntersecting) {

			$(".bloglist").append($msg);
            // 다음 데이터 가져오기: 자연스러운 연출을 위해 setTimeout 사용
            setTimeout(() => {
            	blog_current_page += blog_display_data;
            	printBlogSearch(blog_query, blog_display_data, blog_current_page);
                observer.unobserve(entry.target);
                blog_observeLastChild(observer);

				$msg.remove();
            }, 1000);
        }
    })
}, observer_option)

function blog_observeLastChild(intersectionObserver) {

    const listChildren = document.querySelectorAll(".search-item");
    listChildren.forEach(el => {
        if (!el.nextSibling && blog_current_page < blog_last_page) {
            intersectionObserver.observe(el); // el에 대하여 관측 시작
        } else if (blog_current_page >= blog_last_page) {
            intersectionObserver.disconnect();
        }

    })
}