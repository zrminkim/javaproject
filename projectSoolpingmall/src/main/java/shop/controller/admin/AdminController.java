package shop.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;


@Controller
public class AdminController {
	
	@Autowired
	private DataDao dataDao;
	@Autowired
	private FileValidator fileValidator;
	
	// 매출 총액
	@GetMapping("dashboard")
	public String showDashBoard(HttpServletRequest request, HttpServletResponse response,Model model) {
		int usercnt = dataDao.getUserCount();
		int sales_tot = dataDao.totalSales();
		int revenue_tot = dataDao.totalRevenue();
		List<String > cntr = dataDao.getProduct_cntr();
		List<String > type = dataDao.getProduct_type();
		List<PurchaseDto> purchaseData = dataDao.getPurchaseDataforCharts();
		model.addAttribute("cntrs",cntr);
		model.addAttribute("types",type);
		model.addAttribute("purchaseData",purchaseData);
		model.addAttribute("usercnt",usercnt);
		model.addAttribute("sales",sales_tot);
		model.addAttribute("revenue",revenue_tot);
		return "admin/dashboard";
	}

	
	// 나라별, 종류별
	@GetMapping("dashboardsearch")
	public String showDashBoardSearch(HttpServletRequest request, HttpServletResponse response,ProductBean bean, Model model) {
	
		int usercnt = dataDao.getUserCount();
		int sales_tot = dataDao.totalSales();
		int revenue_tot = dataDao.totalRevenue();
		List<PurchaseDto> purchaseData = dataDao.getPurchaseDataforCharts();
		List<PurchaseDto> purchaseDataSearch = dataDao.getPurchaseDataforChartsSearch(bean);
		for(PurchaseDto p : purchaseDataSearch) {
			System.out.println(p.getPurchase_total());
		}
		List<String >product_cntrs = dataDao.getProduct_cntr();
		List<String >product_types = dataDao.getProduct_type();
		model.addAttribute("cntrs",product_cntrs);
		model.addAttribute("types",product_types);
		
		model.addAttribute("country",bean.getProduct_cntr());
		model.addAttribute("type",bean.getProduct_type());
		model.addAttribute("purchaseData",purchaseData); // 일별 총 매출 
		model.addAttribute("purchaseDataSearch",purchaseDataSearch); // 나라별, 종류별  매출 차트
		model.addAttribute("usercnt",usercnt);
		model.addAttribute("sales",sales_tot);
		model.addAttribute("revenue",revenue_tot);
		return "admin/dashboardSearch";
	}
	@GetMapping("dashboard/{type}") // 일별, 월별 , 년별로 매출 총액 반환
	@ResponseBody
	public List<PurchaseDto> showDashBoardDayofData(HttpServletRequest request, HttpServletResponse response,@PathVariable String type,Model model){
		List<PurchaseDto> dateList = null;
		if(type.equals("today")) {
			dateList = dataDao.getPurchaseDataforCharts();
		}else if(type.equals("month")) {
			dateList = dataDao.getPurchaseDataforMonth();
		}else if(type.equals("year")) {
			dateList = dataDao.getPurchaseDataforYear();
		}
		model.addAttribute("salesDatas",dateList);
		return dateList;
	}

	@GetMapping("adminManager")
	public String showAdminManager(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
			
		Pagination pagination = new Pagination(dataDao.getCount(),page);
		List<ProductDto> list = (List<ProductDto>) dataDao.getProductPageAll(pagination);
		model.addAttribute("products",list);
		model.addAttribute("page",page);
		model.addAttribute("pageVo",pagination);
		return "admin/adminManager";			
	}
	
	@GetMapping("insert")
	public String insertProduct() {
		return "admin/insert";
	}
	@PostMapping("insert")
	public String insertProduct(ProductBean bean, BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		// 업로드 된 파일 검사
		System.out.println(bean.getUploadfile());
		MultipartFile file = bean.getUploadfile();
		fileValidator.validate(file, result);
		String filename = file.getOriginalFilename();
		bean.setProduct_img(filename);
		System.out.println(bean.getProduct_img());
		bean.setProduct_no(dataDao.product_noCnt());
		String filepath = "C:/acorn3project/acorn3project/projectSoolpingmall/src/main/resources/static/images/product/";
		if(result.hasErrors()) {
			return "insert"; //에러 메세지가 넘어간다. -> 에러 발생(파일을 선택하지 않음) 시 수행
		}
		try {
			inputStream = file.getInputStream();
			File newFile = new File(filepath + filename);
			if(newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes,0,read);
			}

		} catch (Exception e) {
			System.out.println("file submit err :" + e);
		}finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	    boolean b = dataDao.insertProductData(bean);
	    if(b) {
	        return "redirect:http://localhost/adminManager";
	    } else {
	        return "redirect:http://localhost/error";
	    }
	}

}
