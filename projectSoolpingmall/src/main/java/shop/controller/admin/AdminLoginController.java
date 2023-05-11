package shop.controller.admin;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.model.admin.AdminDto;
import shop.model.admin.DataDao;

@Controller
public class AdminLoginController {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("adminLogin")
	public String adminLogin() {
		return "/admin/adminLogin";
	}
	
	@PostMapping("adminLogin")
	public String successCheckAdminLogin(HttpSession session, @RequestParam("id")String id, @RequestParam("password")String password, Model model) {
		AdminDto admindto = dataDao.getAdminData(id, password);
		String refId = "";
		String refPwd = "";
		if(admindto != null) {
			refId = admindto.getAdmin_id();
			refPwd = admindto.getAdmin_passwd();
		}
		if(refId.equals(id) && refPwd.equals(password)) {
				session.setAttribute("id", id);
				return "redirect:/dashboard";
		}
		else {
			model.addAttribute("msg","로그인 정보가 올바르지 않습니다");
			return "/admin/adminLogin";
		}
	}
	@GetMapping("adminLogout")
	public String logoutAdmin(HttpSession session) {
		session.removeAttribute("id");
		// session.invalidate();
		return "redirect:localhost/adminLogin";
	}
}
