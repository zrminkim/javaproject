package shop.controller.userinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.common.model.UserDto;
import shop.controller.signup.HashingPasswd;
import shop.model.userinfo.UserInfoDao;

import shop.common.model.PurchaseDto;

@Controller
public class UserInfoController {
	@Autowired
	private UserInfoDao userDao;
	@Autowired
	private HashingPasswd hashingPasswd;
	
	@GetMapping("userInfo")
	public String UserProcess(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		
		UserDto userDto = userDao.selectUserInfo(user_no);

		List<Map<String, String>> list = userDao.selectPurchaseProgress(user_no);
		
		HashMap<String, String> map= new HashMap<>();
		for(int i=0; i<list.size(); i++) {
			String a = String.valueOf(list.get(i).get("count"));
			map.put(list.get(i).get("purchase_progress").toString(), a );
			}
		

		model.addAttribute("user", userDto);
		model.addAttribute("purchaseP", map);
		
		//return "user/userInfo";
		return "user/usermain";
	}
	
	@GetMapping("userCashChagre")
	public String cashCharge(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		
		return "user/usercashcharge";
	}
	
	@PostMapping("chargeProcess")
	public String cashCharge(@RequestParam("cash") String cash, Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		UserDto userDto = userDao.selectUserInfo(user_no);
		System.out.println("cash : " + cash);
		boolean b =  userDao.chargeProgress(user_no, cash);
		System.out.println(b);
		model.addAttribute("user", userDto);
		
		return "user/usercashcharge";
	}
	@GetMapping("userPasswdCheck")
	public String userPasswdcheck(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString(); 
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		
		return "user/userpasswdcheck";
	}
	
	@GetMapping("passwdCheckProcess")
	@ResponseBody
	public String passwdCheckProcess(@RequestParam("passwd") String passwd,  HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString();
		System.out.println("passwd111111 : "+passwd);
		
		String hashedPasswd = hashingPasswd.getHashValue(passwd);
		System.out.println(hashedPasswd);
		
		// 비밀번호, pk와 비교
		System.out.println(userDao.checkPasswd(user_no, hashedPasswd));
		
		return userDao.checkPasswd(user_no, hashedPasswd);
	}
	
	@GetMapping("userInfoModify")
	public String userInfo(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString();
		UserDto userDto = userDao.selectUserInfo(user_no);
		model.addAttribute("user", userDto);
		return "user/userinfo";
	}
	
	@PostMapping("modifyUserInfo")
	public String modifyUserInfo(UserDto been, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String user_no = session.getAttribute("user_no").toString();
		String hashedPasswd = hashingPasswd.getHashValue(been.getUser_passwd());
		been.setUser_passwd(hashedPasswd);
		UserDto userDto = userDao.selectUserInfo(user_no);
		// 비밀번호, pk와 비교
		boolean b= userDao.modifyUserInfo(been);
		model.addAttribute("user", userDto);
		return "redirect:/userinfo";
	}


	
	
	
	
}
