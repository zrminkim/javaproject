package shop.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.common.model.QnaDto;
import shop.model.admin.DataDao;

@Controller
public class QnAController {

	@Autowired
	private DataDao dataDao;

	@GetMapping("qnamanager")
	public String showQnaManager(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		Pagination pagination = new Pagination(dataDao.getQnaCount(), page);
		List<QnaDto> list = (List<QnaDto>) dataDao.getQnaAll(pagination);
		model.addAttribute("qnas", list);
		model.addAttribute("page", page);
		model.addAttribute("pageVo", pagination);
		return "admin/adminManagerQna";
	}

	// 게시판 업데이트

	  @GetMapping("updateQnaSecret") 
	  public String updateQnasecret(@RequestParam("qna_no")int no, Model model) {
	  boolean b = dataDao.updateQnasecret(no); 
	  if(b) { 
		  return "redirect:http://localhost/qnamanager"; 
	  }else { 
		  return "redirect:http://localhost/error"; 
		 } 
	 }
	  
	  @GetMapping("updateQnaApply") 
	  public String updateQnaApply(@RequestParam("qna_no")int no, Model model) {
	  System.out.println(no); 
	  boolean b = dataDao.updateQnaApply(no);  // 답변 변경 사항 확인 후 진행합시다.
	  if(b) { 
		  return "redirect:http://localhost/qnamanager"; 
	  }else { 
		  return "redirect:http://localhost/error"; 
		 } 
	 }
}
