package shop.myaspect.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
public class UserLoginCheck {
	// 세션이 있는지 조회
	public boolean checkUserSession(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		HttpSession session = request.getSession(); // 세션 생성
		
			if (session.getAttribute("user_no") == null) { // 세션이 조회되지 않는 경우 : false
				response.sendRedirect("/login");
				return true;
			} else { // 세션이 조회되는 경우 : true
				return false;
			}
	}
}
