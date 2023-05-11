package shop.controller.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.common.controller.UserBean;
import shop.model.signup.SignUpDao;

@Controller
public class SignUpController {

	private SignUpDao userDao;
	private HashingPasswd hashingPasswd;

	@Autowired
	public SignUpController(SignUpDao userDao, HashingPasswd hashingPasswd) {
		this.userDao = userDao;
		this.hashingPasswd = hashingPasswd;

	}

	// 회원가입 폼으로 이동
	@GetMapping("signUp")
	public String getSignUp() {
		return "login/signUp";
	}

	// 아이디 중복 확인
	@GetMapping("signUp/checkId")
	@ResponseBody
	public boolean checkId(@RequestParam String user_id) {
		boolean b = userDao.selectUserIdById(user_id);
		if (b) { // 중복된 아이디가 있으면
			return true;

		} else {// 중복된 아이디가 없으면
			return false;
		}
	}

	// 회원가입 처리 후 결과페이지 출력
	@PostMapping("signUp")
	public String postSignUp(UserBean userBean, RedirectAttributes attributes) throws Exception {

		// 입력받은 비밀번호를 해싱한 후에 userBean에 세팅(회원정보 저장용)
		String hashedPasswd = hashingPasswd.getHashValue(userBean.getUser_passwd());
		userBean.setUser_passwd(hashedPasswd);

		/*
		 * String salt = hashingPasswd.createSalt();
		 * String hashedPasswd = hashingPasswd.getHashValue(userBean.getUser_passwd(),
		 * salt);
		 * userBean.setUser_passwd(hashedPasswd);
		 */

		// attributes.addAttribute("user_id",userBean.getUser_id());

		if (userDao.insertUser(userBean)) { // 회원정보 저장 성공하면 ###########
			return "redirect:/signUpSuccess?user_id=" + userBean.getUser_id(); // 회원가입 성공 페이지로 이동
		} else { // 회원정보 저장 실패하면
			return "redirect:/err"; // 에러 페이지로 이동
		}
	}

	// 회원가입 성공 시 성공 페이지 출력
	@GetMapping("/signUpSuccess")
	public String getSignUpSuccess(@RequestParam("user_id") String user_id, Model model) {
		model.addAttribute("data", user_id);
		return "login/signUpSuccess";
	}

}
