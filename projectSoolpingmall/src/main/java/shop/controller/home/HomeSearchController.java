package shop.controller.home;

import java.util.List;

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
public class HomeSearchController {

	@Autowired
	private HomeDataDao homeDataDao;
	
	
	@GetMapping("homeSearch")
	public String searchProcess(HttpServletRequest request, HttpServletResponse response, HomeBean bean, Model model,
			@RequestParam(value="page",defaultValue = "1")int page) {
		
		try {
			Pagination pagination = new Pagination(homeDataDao.getDetailCount(bean),page);
			pagination.setSearchProduct(bean.getSearchProduct());
			pagination.setSearchValue(bean.getSearchValue());
			List<ProductDto> plist = (List<ProductDto>)homeDataDao.detailSearchProduct(pagination);
			model.addAttribute("searchProduct",pagination.getSearchProduct());
			model.addAttribute("searchValue",pagination.getSearchValue());
			model.addAttribute("products",plist);
			model.addAttribute("page",page);
			model.addAttribute("pageVo",pagination);
			return "main/homeSearch";
		} catch (Exception e) {
			return "main/error";
		}
	}
}
/*이 코드는 Spring MVC 프레임워크에서 HomeSearchController 클래스를 정의하고, @Controller 어노테이션을 사용하여 해당 클래스가 컨트롤러임을 명시하고 있습니다.

@GetMapping("homeSearch") 어노테이션은 "/homeSearch" 경로에 대한 GET 요청을 처리하도록 메서드를 지정하고 있습니다.

메서드의 파라미터로 HomeBean, Model, HttpServletResponse, HttpServletRequest, @RequestParam 어노테이션이 사용되고 있습니다.

HomeBean은 검색어와 관련된 정보를 담고 있습니다.

Model 객체는 뷰에 전달할 데이터를 담고 있습니다. 여기서는 DB에서 가져온 검색 결과를 담아 뷰로 전달하고 있습니다.

@RequestParam 어노테이션을 사용하여, "page" 파라미터를 기본값 1로 설정하여 페이지 번호를 전달하고 있습니다.

DB에서 검색 결과를 가져오는 HomeDataDao 객체는 @Autowired 어노테이션을 사용하여 자동으로 의존성 주입이 이루어지고 있습니다.

마지막으로, 뷰 이름인 "main/homeSearch"을 반환하고 있습니다.*/