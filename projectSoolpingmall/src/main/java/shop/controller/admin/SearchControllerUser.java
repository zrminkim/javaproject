package shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;
import shop.common.model.UserDto;

@Controller
public class SearchControllerUser {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("adminManagerUserSearch")
	public String searchProcess(UserBean bean, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
		Pagination pagination = new Pagination(dataDao.getUserDetailCount(bean),page);
		pagination.setSearchUser(bean.getSearchUser());
		pagination.setSearchValue(bean.getSearchValue());
		List<UserDto> plist = (List<UserDto>)dataDao.detailUserSearchProduct(pagination);
		model.addAttribute("searchProduct",pagination.getSearchProduct());
		model.addAttribute("searchValue",pagination.getSearchValue());
		model.addAttribute("users",plist);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManagerUserSearch";
	}
}
