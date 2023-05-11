package shop.controller.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.common.controller.UserBean;
import shop.common.model.UserDto;
import shop.model.login.LoginDao;


@Controller

public class SearchIdController {
	
	private LoginDao loginDao;
	
	@Autowired
	public SearchIdController(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	
	// 아이디 찾기 폼으로 이동
	@GetMapping("findId")
	public String getFindId() {
		return "login/findId";
	}
	
	
	/*
	// 입력받은 이메일에 해당하는 id가 있는지 검사
	@PostMapping
	public String postFindId(@RequestParam String user_email,Model model) {
		String user_id = loginDao.selectUserIdByEmail(user_email);
		if(user_id!=null) {
			model.addAttribute("data", user_id);
		}
		return "findIdResult";			
	}
	*/
	
	
	@PostMapping("findId")
	@ResponseBody
	public String postFindId(@RequestParam Map<String, Object> vo) { // ajax에서 Map을 받고
		String user_id  = loginDao.selectUserIdByEmail((String)vo.get("user_email")); // Map에서 꺼낸 이메일로 아이디 조회
		if(user_id != null) {   //찾은 경우 :  아이디를 반환
			return user_id;
		} else {				//찾지 못한 경우 : "x"를 반환(ajax로 받아서 모달창 띄울 목적)
			return "x";
		}
		
	}
	
	
	// 회원을 찾은 경우 : 유저 아이디를 가지고 결과 페이지로 이동
	@GetMapping("findIdResult")
	public String getFindIdResult(@RequestParam String user_id, Model model) {
		System.out.println("userid : "+user_id);
		model.addAttribute("data", user_id);
		return "login/findIdResult";
	}

	
}
