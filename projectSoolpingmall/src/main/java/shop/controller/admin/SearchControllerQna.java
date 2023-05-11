package shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;
import shop.common.model.QnaDto;
import shop.common.model.UserDto;

@Controller
public class SearchControllerQna {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("adminManagerQnaSearch")
	public String searchProcess(QnaBean bean, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
		Pagination pagination = new Pagination(dataDao.getQnaDetailCount(bean),page);
		pagination.setSearchSubject(bean.getSearchSubject());
		pagination.setSearchValue(bean.getSearchValue());
		List<QnaDto> plist = (List<QnaDto>)dataDao.detailQnaSearchProduct(pagination);
		model.addAttribute("searchSubject",pagination.getSearchSubject());
		model.addAttribute("searchValue",pagination.getSearchValue());
		model.addAttribute("qnas",plist);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManagerQnaSearch";
	}
}
