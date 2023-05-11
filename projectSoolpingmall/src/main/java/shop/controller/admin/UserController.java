package shop.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;
import shop.common.model.UserDto;


@Controller
public class UserController {
	
	@Autowired
	private DataDao dataDao;
		
	
	@GetMapping("usermanager")
	public String showUserManager(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getUserCount(),page);
		
		List<UserDto> list = (List<UserDto>)dataDao.getUserAll(pagination);
		model.addAttribute("users",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManagerUser";			
	}
	
	@GetMapping("updateUser")
	public String insertProduct(@RequestParam("user_no")int no, Model model) {
		boolean b = dataDao.updateUser(no);
		if(b) {
			return "redirect:http://localhost/usermanager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
	
	

}
