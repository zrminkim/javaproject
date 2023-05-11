package shop.controller.admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.DataDao;
import shop.common.model.AuctionDto;
import shop.common.model.ProductDto;

@Controller
public class AuctionController {
	@Autowired
	private DataDao dataDao;
		
	@GetMapping("auctionmanager")
	public String showAuctionManager(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		Pagination pagination = new Pagination(dataDao.getAuctionCount(), page);

		List<AuctionDto> list = (List<AuctionDto>) dataDao.getAuctionPageAll(pagination);
		model.addAttribute("auctions", list);
		model.addAttribute("page", page);
		model.addAttribute("pageVo", pagination);
		return "admin/adminManagerAuction";
	}
	
	@GetMapping("auction")
	public String detailAuction(@RequestParam("product_no")String num,Model model) {
		model.addAttribute("product_num",num);
		return "admin/insertAuction";
	}

	@PostMapping("insertauction")
	public String insertAuction(AuctionBean bean) {
		System.out.println(bean.getAuction_open());
		System.out.println(bean.getProduct_num());
		boolean b = dataDao.insertAuctionData(bean);
		if(b) {
			return "redirect:http://localhost/auctionmanager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
	@GetMapping("updateAuction")
	public String updateAuction(@RequestParam("auction_no")String auction_no, Model model) {
		int no = Integer.parseInt(auction_no);
		System.out.println(no);
		AuctionDto dto = dataDao.searchAuction(no);
		model.addAttribute("auction",dto);
		return "admin/updateAuction";
	}
	
	@PostMapping("updateauction")
	public String updateAuctionProcess(AuctionBean bean) {
		System.out.println(bean.getAuction_open());
		System.out.println(bean.getProduct_num());
		boolean b = dataDao.updateAuctiontData(bean);
		if(b) {
			return "redirect:http://localhost/auctionmanager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
	  @GetMapping("updateAuctionStateProgress") 
	  public String updateAuctionStateprogress(@RequestParam("auction_no")int no, Model model) {
	  boolean b = dataDao.updateAuctionStateProgress(no); 
	  if(b) { 
		  return "redirect:http://localhost/auctionmanager"; 
	  }else { 
		  return "redirect:http://localhost/error"; 
		 } 
	 }

}
