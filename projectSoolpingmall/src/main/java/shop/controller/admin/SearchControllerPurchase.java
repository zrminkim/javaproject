package shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.UserDto;

@Controller
public class SearchControllerPurchase {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("adminManagerPurchaseSearch")
	public String searchProcess(PurchaseBean bean, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
		Pagination pagination = new Pagination(dataDao.getPurchaseDetailCount(bean),page);
		pagination.setSearchPurchase(bean.getSearchPurchase());
		pagination.setSearchValue(bean.getSearchValue());
		List<PurchaseDto> plist = (List<PurchaseDto>)dataDao.detailPurchaseSearchProduct(pagination);
		model.addAttribute("searchPurchase",pagination.getSearchProduct());
		model.addAttribute("searchValue",pagination.getSearchValue());
		model.addAttribute("purchases",plist);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManagerPurchaseSearch";
	}
}
