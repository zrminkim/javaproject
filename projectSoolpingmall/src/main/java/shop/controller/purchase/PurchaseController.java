package shop.controller.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import shop.model.cart.CartDto;
import shop.model.purchase.PurchaseBean;
import shop.model.purchase.PurchaseDao;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.UserDto;
import shop.controller.admin.ProductBean;
import shop.controller.admin.UserBean;

@Controller
@Slf4j
public class PurchaseController {
	@Autowired
	PurchaseDao purchaseDao;

	@PostMapping("purchase") // 결제하기 페이지로 이동
	public String purchaseLink(@RequestParam("cart_cnt[]") int[] cart_cnt, @RequestParam("cart_no[]") int[] cart_no,
			Model model) {
		for (int i = 0; i < cart_cnt.length; i++) {
		
			CartDto cartDto = new CartDto();
			cartDto.setCart_cnt(cart_cnt[i]);
			cartDto.setCart_no(cart_no[i]);
			purchaseDao.updateCartData(cartDto);
		}
		return "redirect:/checkout";
	}

	@GetMapping("/checkout") // PRG 패턴 적용 메소드
	public String checkOutPRG(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());
		List<UserDto> ulist = purchaseDao.getUserData(user_num);

		ArrayList<CartDto> clist = (ArrayList<CartDto>) purchaseDao.getCartData(user_num);

		String path = "a"; // 링크 이동경로 판별 변수
		int sum = 0; // 결제 총액을 저장할 변수

		for (int i = 0; i < clist.size(); i++) {
			sum += clist.get(i).getTotal();		// 장바구니 합계 계산
		}
		
		// 유저 정보, 장바구니 상품 정보, 총액을 결제 페이지로 넘기기
		model.addAttribute("ulist", ulist);
		model.addAttribute("clist", clist);
		model.addAttribute("sum", sum);
		model.addAttribute("path", path);

		return "purchase/checkout";
	}

	@PostMapping("purchaseDirect")
	public String purchaseDirectProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam("product_no") String product_no,
			@RequestParam("cnt") int cnt) {
		HttpSession session = request.getSession();
		session.setAttribute("cnt", cnt);	// 개별 상품페이지에서 직접 구매시 수량을 개별 변수로 세션에 저장

		return "redirect:/checkoutDirect?cnt=" + cnt + "&product_no=" + product_no;

	}

	@GetMapping("checkoutDirect")
	public String purchaseDirectPRG(HttpServletRequest request, HttpServletResponse response, @RequestParam("product_no") String product_no, @RequestParam("cnt") int cnt, Model model) {

		HttpSession session = request.getSession();
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());
		List<ProductDto> clist = purchaseDao.getProductData(product_no);
		List<UserDto> ulist = purchaseDao.getUserData(user_num);
		
		String path = "b"; // 링크 이동경로 판별 변수
		
		model.addAttribute("ulist", ulist);
		model.addAttribute("clist", clist);
		model.addAttribute("cnt", cnt);
		model.addAttribute("path", path);
		return "purchase/checkout";
	}

	@PostMapping("orderResult") // 결제완료 페이지로 이동
	public String orderResultProcess(HttpServletRequest request, PurchaseBean bean, UserBean userBean, 
			@RequestParam("user_cash_used") int user_cash_used, @RequestParam("user_point_used") int user_point_used, 
			@RequestParam("purchase_total") int purchase_total, @RequestParam("product_no") int product_no) {
		HttpSession session = request.getSession();
		int cnt = (int) session.getAttribute("cnt");

		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());

		bean.setUser_num(user_num);
		int updateCash = userBean.getUser_cash() - user_cash_used; // 차감 후 남은 캐쉬 계산
		int updatePoint = userBean.getUser_point() - user_point_used; // 차감 후 남은 포인트 계산
		
		boolean b = false;

		
		String purchase_orderno = purchaseDao.ordernoCreater(user_num);		// 주문번호 생성
		bean.setPurchase_orderno(purchase_orderno);
		b = purchaseDao.insertPurchaseData(bean);
		
		if (b == false)
			return "redirect:/";
		

		try {
			purchaseDao.updateUserCash(updateCash, updatePoint, user_num);	// 유저의 캐쉬, 포인트 업데이트
		} catch (Exception e) {
			System.out.println("err : " + e);
		}
		
		if(purchaseDao.countCart() == 0) {	// 카트를 경유하지 않을 시 적용

			purchaseDao.updateProductStock(cnt, product_no);	// 결제 완료 페이지로 이동하기 전 재고 업데이트
			purchaseDao.insertPurchaseList(product_no, bean.getPurchase_no(), cnt);
		} 
		
		ArrayList<CartDto> cartlist = (ArrayList<CartDto>)purchaseDao.getCartData(user_num);	//카트에서 주문시 적용
		for (CartDto cartDto : cartlist) {
		    int cart_cnt = cartDto.getCart_cnt();
		    int product_num = cartDto.getProduct_num();
		    purchaseDao.updateProductStock(cart_cnt, product_num);
		    purchaseDao.insertPurchaseList(product_num, bean.getPurchase_no(), cart_cnt);
			}
		
			
		if (b) {
			return "redirect:/orderResult?user_cash_used=" + user_cash_used + "&user_point_used=" + 
		user_point_used + "&purchase_num=" + bean.getPurchase_no() + "&purchase_total=" + purchase_total + "&cnt=" +cnt;
		} else {
			return "redirect:/";
		}

	}

	@GetMapping("/orderResult") // PRG 패턴 적용 메소드
	public String orderResultPRG(@RequestParam("purchase_num") String purchase_num, @RequestParam("user_cash_used") int user_cash_used, 
			@RequestParam("user_point_used") int user_point_used, @RequestParam("purchase_total") int purchase_total, @RequestParam("cnt") int cnt, 
			HttpServletRequest request, HttpServletResponse response, Model model) {	
		HttpSession session = request.getSession();
		int user_num = Integer.parseInt(session.getAttribute("user_no").toString());

		System.out.println("abc : " + purchase_num);
		ArrayList<PurchaseBean> plist = (ArrayList<PurchaseBean>)purchaseDao.selectPurchaseList(purchase_num);
		List<PurchaseBean> plist2 = purchaseDao.getPurchaseData2(purchase_num);
		
		
		model.addAttribute("plist2", plist2);
		model.addAttribute("plist", plist);
		model.addAttribute("user_cash_used", user_cash_used);
		model.addAttribute("user_point_used", user_point_used);
		model.addAttribute("purchase_total", purchase_total);
	//	System.out.println(plist);
		if(purchaseDao.countCart() == 0) {
			String purchaseDirect = "a";	// 상품 상세 페이지에서 직접 구매하는 경우에 결제 완료 페이지의 수량 출력을 위한 변수
			model.addAttribute("cnt", cnt);
			model.addAttribute("path", purchaseDirect);

		} else {
			String purchaseDirect = "b";
			model.addAttribute("path", purchaseDirect);
			try {
				purchaseDao.deleteCartProduct(user_num);	// 카트에서 주문시 결제 후 카트 내역 삭제
			} catch (Exception e) {
				System.out.println("err : " + e);
			}
		}
		return "purchase/orderResult";
	}

}
