package shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;

@Controller
public class SearchController {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("adminManagerSearch")
	public String searchProcess(ProductBean bean, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
		Pagination pagination = new Pagination(dataDao.getDetailCount(bean),page);
		pagination.setSearchProduct(bean.getSearchProduct());
		pagination.setSearchValue(bean.getSearchValue());
		List<ProductDto> plist = (List<ProductDto>)dataDao.detailSearchProduct(pagination);
		model.addAttribute("searchProduct",pagination.getSearchProduct());
		model.addAttribute("searchValue",pagination.getSearchValue());
		model.addAttribute("products",plist);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManagerSearch";
	}
}
