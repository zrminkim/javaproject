package shop.controller.category;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.common.model.ProductDto;
import shop.common.model.TimedealSelectDto;
import shop.controller.admin.FileValidator;
import shop.controller.category.Pagination;
import shop.model.home.HomeDataDao;

@Controller
public class CategoryController {
	@Autowired
	private HomeDataDao dataDao;
	
	@Autowired
	private FileValidator fileValidator;
	
	

	 @GetMapping("/sojucategory")
	 public String showAdminManager(HttpServletRequest request, HttpServletResponse response, Model model,
	 @RequestParam(value="page",defaultValue = "1")int page) {
		 
	 Pagination pagination = new Pagination(dataDao.getCount(),page);
	 List<ProductDto> list = (List<ProductDto>) dataDao.getDataSojuCategory(pagination);
	 List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("soju");
	 
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		
		model.addAttribute("latest", plist);
	 
	 model.addAttribute("timedeal",timeList);
	 model.addAttribute("products",list);
	 model.addAttribute("page",page);
	 model.addAttribute("pageVo",pagination);
	 return "category/sojucategory";
 }
	 
	
	
	
	@GetMapping("/beercategory")
	public String showAdminManager1(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataBeerCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("beer");
		model.addAttribute("latest", plist);
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/beercategory";			
	}
	
	
	@GetMapping("/winecategory")
	public String showAdminManager2(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataWineCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("wine");
		model.addAttribute("latest", plist);
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/winecategory";			
	}
	
	
	@GetMapping("/wiskeycategory")
	public String showAdminManager3(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataWiskeyCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("whiskey");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/wiskeycategory";			
	}
	
	
	@GetMapping("/vodkacategory")
	public String showAdminManager4(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataVodkaCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("vodka");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/vodkacategory";			
	}
	
	@GetMapping("/liqueurcategory")
	public String showAdminManager5(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataLiqueurCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("liqueur");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/liqueurcategory";			
	}
	
	@GetMapping("/chinacategory")
	public String showAdminManager6(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataChinaCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("gin");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/chinacategory";			
	}
	
	
	@GetMapping("/brandicategory")
	public String showAdminManager7(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataBrandiCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("brandy");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/brandicategory";			
	}
	
	
	@GetMapping("/japancategory")
	public String showAdminManager8(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataJapanCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("sake");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/japancategory";			
	}
	
	@GetMapping("/tequilacategory")
	public String showAdminManager9(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataTequilaCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("tequilla");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/tequilacategory";			
	}
	
	@GetMapping("/champagnecategory")
	public String showAdminManager10(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		
		List<ProductDto> list = (List<ProductDto>) dataDao.getDataChampagneCategory(pagination);
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		List<TimedealSelectDto> timeList = (List<TimedealSelectDto>) dataDao.getTimedealProduct("rum");
		
		model.addAttribute("timedeal",timeList);
		model.addAttribute("latest", plist);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "category/champagnecategory";			
	}	
}
