package shop.controller.home;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.home.HomeDataDao;
import shop.common.model.ProductDto;

@Controller
public class HomeController {
	@Autowired
	private HomeDataDao dataDao;
	
	@GetMapping("/")
	public String showAdminManager(HttpServletRequest request, HttpServletResponse response, Model model,
	@RequestParam(name = "page", defaultValue = "1") int page){
		
	try {
		ArrayList<ProductDto> list = (ArrayList<ProductDto>) dataDao.getDataRecommendCategory();
		ArrayList<ProductDto> list1	= (ArrayList<ProductDto>) dataDao.getDataRecommendCategory1();
		ArrayList<ProductDto> list2	= (ArrayList<ProductDto>) dataDao.getDataRecommendCategory2();
		ArrayList<ProductDto> list3	= (ArrayList<ProductDto>) dataDao.getDataRecommendCategory3();
		
		ArrayList<ProductDto> plist	= (ArrayList<ProductDto>) dataDao.getDataLatestCategory();
		ArrayList<ProductDto> plist1 = (ArrayList<ProductDto>) dataDao.getDataLatestCategory1();
		ArrayList<ProductDto> plist2 = (ArrayList<ProductDto>) dataDao.getDataLatestCategory2();
		
		model.addAttribute("products1", list1);//이달의 추천
		model.addAttribute("products2", list2);//이달의 추천
		model.addAttribute("products3", list3);//이달의 추천
		
		model.addAttribute("latest", plist);
		model.addAttribute("toplate", plist1);
		model.addAttribute("review", plist2);
		
		model.addAttribute("page",page);
		model.addAttribute("products",list);
		return "main/home";
	} catch (Exception e) {
		model.addAttribute("error", "An error occurred while processing your request.");
		return "main/error";
	}
		
	}	
	
}
/*이 코드는 Spring MVC 프레임워크에서 HomeController 클래스를 정의하고, @Controller 어노테이션을 사용하여 해당 클래스가 컨트롤러임을 명시하고 있습니다.

@GetMapping("/") 어노테이션은 "/" 경로에 대한 GET 요청을 처리하도록 메서드를 지정하고 있습니다.

메서드의 파라미터로 HttpServletRequest, HttpServletResponse, Model, @RequestParam 어노테이션이 사용되고 있습니다.

Model 객체는 뷰에 전달할 데이터를 담고 있습니다. 여기서는 DB에서 가져온 추천 상품 리스트와 최신 상품 리스트를 담아 뷰로 전달하고 있습니다.

@RequestParam 어노테이션을 사용하여, "page" 파라미터를 기본값 1로 설정하여 페이지 번호를 전달하고 있습니다.

DB에서 상품 리스트를 가져오는 HomeDataDao 객체는 @Autowired 어노테이션을 사용하여 자동으로 의존성 주입이 이루어지고 있습니다.

마지막으로, 뷰 이름인 "main/home"을 반환하고 있습니다.*/

