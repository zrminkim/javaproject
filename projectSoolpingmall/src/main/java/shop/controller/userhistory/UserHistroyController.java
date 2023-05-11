package shop.controller.userhistory;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.userinfo.UserInfoDao;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.model.userhistory.UserHistoryDao;
import shop.model.userhistory.UserReplyDto;
import shop.common.model.UserDto;

@Controller
public class UserHistroyController {
	@Autowired
	private UserHistoryDao userHistoryDao;
	@Autowired
	private UserInfoDao userDao;
	
	@GetMapping("userPurchaseList")
	public String userPurchaseList(Model model, @RequestParam(value="page",defaultValue = "1")int page, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		PaginationUser paginationUser = new PaginationUser(userHistoryDao.getCountPurchase(user_no), page);
		paginationUser.setUser_no(user_no);
		ArrayList<PurchaseDto> list = (ArrayList<PurchaseDto>) userHistoryDao.selectPurchaseList(paginationUser);
		
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		
		model.addAttribute("purchase",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",paginationUser);
		
		return "user/userpurchasehistory";
	}

	@GetMapping("userWishList")
	public String userWishList(Model model, @RequestParam(value="page",defaultValue = "1")int page, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		PaginationUser paginationUser = new PaginationUser(userHistoryDao.getCountProduct(user_no), page);
		paginationUser.setUser_no(user_no);
		ArrayList<ProductDto> list = (ArrayList<ProductDto>) userHistoryDao.userWishList(paginationUser);
		
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",paginationUser);
		
		return "user/userwishlist";
	}	
	
	@PostMapping("delwish")
	public String delwish(Model model, @RequestParam(value="page",defaultValue = "1")int page, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		
		return "redirect:/userWishList";
	}	
	
	@GetMapping("userRelpyList")
	public String userRelpyList(Model model, @RequestParam(value="page",defaultValue = "1")int page, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		System.out.println("user"+ user_no);
		PaginationUser paginationUser = new PaginationUser(userHistoryDao.getCountReply(user_no), page);
		paginationUser.setUser_no(user_no);
		ArrayList<UserReplyDto> list = (ArrayList<UserReplyDto>) userHistoryDao.userRelpyList(paginationUser);
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		model.addAttribute("reply",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",paginationUser);
		System.out.println("list "+list.size());
		return "user/userreply";
	}	
	
	@PostMapping("userwritereply")
	public String userRelpy(@RequestParam(value="product_no")String product_no, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString();
		model.addAttribute("product_no",product_no); 
		
		return "user/userwrite";
	}	
	
	
}
