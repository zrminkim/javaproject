package shop.controller.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.common.model.UserDto;
import shop.controller.signup.HashingPasswd;
import shop.model.login.LoginDao;

@Controller
@RequestMapping("findPasswd")
public class SearchPasswdController {

	private LoginDao loginDao;
	private VerifyEmail verifyEmail;
	private HashingPasswd hashingPasswd;
	
	@Autowired
	public SearchPasswdController(LoginDao loginDao, VerifyEmail verifyEmail, HashingPasswd hashingPasswd) {
		this.loginDao = loginDao;
		this.verifyEmail = verifyEmail;
		this.hashingPasswd = hashingPasswd;
	}

	
	// 비밀번호 찾기 폼으로 이동
	@GetMapping
	public String getFindPasswd() {
		return "login/findPasswd";
	}
	
	
	@PostMapping
	@ResponseBody
	public String postFindPasswd(@RequestParam Map<String, Object> vo, Model model) throws Exception{
		
		String user_id = (String)vo.get("user_id");
		String user_email = (String)vo.get("user_email");
		
		// id와 email로 일치하는 회원 객체를 찾고
		UserDto dto = loginDao.selectUserByIdAndEmail(user_id, user_email);
		
		if(dto!=null) {		 // 회원객체가 있다면
			// 임시비밀번호 이메일로 전송
			String newPasswd = verifyEmail.sendEmail(user_email);
			
			// 해싱한 임시비밀번호로 비밀번호 갱신
			String hashedPasswd = hashingPasswd.getHashValue(newPasswd);
			boolean b = loginDao.updatePasswd(hashedPasswd, dto.getUser_no());
			if(b) {
				System.out.println("비번갱신 성공");
			} else {
				System.out.println("비번갱신 실패");
			}
			return "o";
			
		} else { 			// 회원객체가 없다면
			return "x";
		}
		

	}
	
}
