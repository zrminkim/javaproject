package shop.controller.cart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.model.cart.CartDao;
import shop.model.cart.CartDto;
import shop.common.model.UserDto;

@Controller
public class CartController {
	@Autowired
	private CartDao cartDao;
	
	@GetMapping("cart") // 'cart' 요청시 장바구니 페이지로 이동
	public String cartDirect(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		HttpSession session = request.getSession();
		
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());
		
		//System.out.println(user_num);
		ArrayList<CartDto> list = (ArrayList<CartDto>) cartDao.getDataAll(user_num); // user_num 임의로 부여. 로그인 완성 후 값 받아오도록 수정
		int user_cash = cartDao.selectUserCash(user_num);
		int totalSum = 0; // 장바구니 내 상품들의 총합 구하기
		for (int i = 0; i < list.size(); i++) {
			totalSum += list.get(i).getTotal();
		}

		// 장바구니 내 상품 정보와 총 금액 전달
		model.addAttribute("cartProductList", list);
		model.addAttribute("totalSum", totalSum);
		model.addAttribute("user_cash", user_cash);
		return "purchase/shoping-cart.html";
	}

	@GetMapping("cartdelete") // 장바구니 내 항목 삭제
	@ResponseBody
	public String deleteCartProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam("product_num") int product_num) { // 테이블 확정 후 유저 아이디 추가
		
		HttpSession session = request.getSession();
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());

		boolean b = cartDao.deleteCartProduct(product_num, user_num);
		if (b) { // 삭제 성공시 장바구니 갱신
			return "redirect:/purchase/shoping-cart";
		} else { // 실패시 메인으로 이동(에러페이지 작성완료시 에러 페이지로 변경)
			return "redirect:/main";
		}
	}

	@PostMapping("cartUpdate")
	@ResponseBody
	public String cartUpdate(@RequestBody List<CartDto> cart) {

	//	System.out.println(cart);

		return "cart";

	}
	
	@GetMapping("addCart")
	public String addCartProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam("product_num") int product_num, @RequestParam("cart_cnt") int cart_cnt) {

		HttpSession session = request.getSession();
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());
		List<CartDto> list = cartDao.selectCartbyProductNo(product_num, user_num);
		boolean b = false;
		if(list.isEmpty()) {
			b = cartDao.addCartProcess(user_num, product_num, cart_cnt);
		} else {
			b = cartDao.UpdateCartCnt(cart_cnt, product_num);
		}

		if (b) { // 삭제 성공시 장바구니 갱신
			return "redirect:/";
		} else { // 실패시 메인으로 이동(에러페이지 작성완료시 에러 페이지로 변경)
			return "redirect:/error";
		}
	}
}