package shop.controller.detail;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.model.detail.DetailDao;
import shop.model.detail.DetailDto;
import shop.model.detail.ReviewDao;
import shop.model.detail.ReviewDto;

@Controller
public class DetailController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private DetailDao detailDao;
	
	//상품 클릭시 자료 열람 - 컨트롤러
	@GetMapping("productdetail")
	public String DetailListAll(HttpServletRequest request, @RequestParam("product_no")String product_no, Model model,
	@RequestParam(value="page",defaultValue = "1")int page) {

		HttpSession session = request.getSession();
		
		String user_no = session.getAttribute("user_no").toString(); // session으로 값 받기 DB연결
		ReviewPagination reviewpagination = new ReviewPagination(reviewDao.getCount(product_no),page);
		
		
		// 조회수 올리기
		boolean bb = detailDao.updateViews(product_no);

		// 상품 상세 정보 DB에서 읽기
		DetailDto dto = detailDao.detailListAll(product_no);

		// 찜하기 읽기
		boolean b = detailDao.wishlist(user_no, product_no);
		
		// 리뷰 데이터 페이징 처리
		reviewpagination.setProduct_no(product_no);
		List<ReviewDto> list = (List<ReviewDto>)reviewDao.listReview(reviewpagination);
		//logger.info("review" + list);
		// 개별 상품에 별 갯수 불러오기
		

		model.addAttribute("page",page);
		model.addAttribute("pageVo",reviewpagination);
		
		
		
		model.addAttribute("datas", dto);
		
		model.addAttribute("reviews", list);

		model.addAttribute("wishcheck", b);
		return "detail/detail";
	}
	@GetMapping("wishInput")
	public String wishInput(HttpServletRequest request, @RequestParam("product_no")String product_no) {

		HttpSession session = request.getSession();
		
		String user_no = session.getAttribute("user_no").toString(); // session으로 값 받기 DB연결
		// 찜하기 
		boolean bb = detailDao.wishIn(user_no, product_no);
		//logger.info("b는" + b);
		return "";
	}

	@GetMapping("wishOutput")
	public String wishOutput(HttpServletRequest request, @RequestParam("product_no")String product_no) {

		HttpSession session = request.getSession();
		
		String user_no = session.getAttribute("user_no").toString(); // session으로 값 받기 DB연결

		// 찜하기 해제
		boolean b = detailDao.wishOut(user_no,product_no);
		//logger.info("b는" + b);
		return "";
	}



	// 리뷰 삭제
	@ResponseBody
	@GetMapping("delReview")
	public String reviewDel(@RequestParam("reply_no")String reply_no) {

		boolean b = reviewDao.deleteReview(reply_no);
		if(b) {
			return "productdetail?product_no=1";
		}
			return "redirect:error";
	}

}

