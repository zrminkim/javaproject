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
import shop.common.model.TimedealDto;

@Controller
public class TimeDealController {
	@Autowired
	private DataDao dataDao;
		
	
	 @GetMapping("timedealmanager") 
	 public String showTimedealManager(HttpServletRequest request, HttpServletResponse response, Model model, 
			 	@RequestParam(value = "page", defaultValue = "1") int page) {
	  
	 Pagination pagination = new Pagination(dataDao.getTimeDealCount(), page);
	 List<TimedealDto> list = (List<TimedealDto>)dataDao.getTimedealPageAll(pagination); 
	 model.addAttribute("timedeals", list);
	 model.addAttribute("page", page); model.addAttribute("pageVo", pagination);
	  return "admin/adminManagerTimeDeal"; 
	 }
	 
	@GetMapping("timedeal")
	public String insertTimedeal(@RequestParam("product_no")String no,Model model) {
		model.addAttribute("product_no",no);
		return "admin/insertTime";
	}

	@PostMapping("inserttimedeal")
	public String insertTimedeal(TimeDealBean bean) {
		
		boolean b = dataDao.insertTimedealData(bean);
		if(b) {
			return "redirect:http://localhost/timedealmanager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
	
	  @GetMapping("updateTimedeal") 
	  public String updateTimedeal(@RequestParam("product_no")String product_no, Model model) {
	  int no = Integer.parseInt(product_no); 
	  TimedealDto dto = dataDao.searchTimedeal(no); 
	  model.addAttribute("timedeals",dto); 
	  return "admin/updateTimedeal"; 
	  }
	 
	  @PostMapping("updateTimedeal") 
	  public String updateTimedealProcess(TimeDealBean bean) { 
	   boolean b = dataDao.updateTimedealData(bean); 
	   if(b) { 
		  return "redirect:http://localhost/timedealmanager"; 
	   }
	   else { 
		   return "redirect:http://localhost/error"; 
		   } 
	   }
	  
	  @GetMapping("updateTimeDealStateProgress") 
	  public String updateTimedealchange(@RequestParam("product_no")int no, Model model) { 
		  boolean b = dataDao.updateTimedealStateProgress(no); 
		  if(b) { 
			  return "redirect:http://localhost/timedealmanager"; 
			  }
		  else { 
			  return "redirect:http://localhost/error"; 
			  } 
		  }
}
