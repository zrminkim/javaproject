package shop.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.common.model.PurchaseDto;
import shop.common.model.QnaDto;
import shop.model.admin.DataDao;

@Controller
public class AdminPurchaseController {

	@Autowired
	private DataDao dataDao;

	@GetMapping("purchasemanager")
	public String showPurchaseManager(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		Pagination pagination = new Pagination(dataDao.getPurchaseCount(), page);

		List<PurchaseDto> list = (List<PurchaseDto>) dataDao.getPurchaseAll(pagination);
		model.addAttribute("purchases", list);
		model.addAttribute("page", page);
		model.addAttribute("pageVo", pagination);
		return "admin/adminManagerPurchase";
	}

	// 주문 배송관리 업데이트
	  @GetMapping("updatePurchaseProgress") 
	  public String updatePurchaseprogress(@RequestParam("purchase_no")int no, Model model) {
	  boolean b = dataDao.updatePurchaseProgress(no); 
	  if(b) { 
		  return "redirect:http://localhost/purchasemanager"; 
	  }else { 
		  return "redirect:http://localhost/error"; 
		 } 
	 }
}
