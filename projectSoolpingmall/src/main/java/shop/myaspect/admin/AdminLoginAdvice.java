package shop.myaspect.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminLoginAdvice {
	@Autowired
	private AdminLoginCheck adminLoginCheck;
	
	@Around("execution(* shop.controller.admin.AdminController.showDashBoard*(..) )")
	public Object adminLogin(ProceedingJoinPoint joinPoint) throws Throwable{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		
		for(Object obj:joinPoint.getArgs()) {
			if(obj instanceof HttpServletRequest) {
				request = (HttpServletRequest)obj;
			}
			if(obj instanceof HttpServletResponse) {
				response = (HttpServletResponse)obj;
			}
		}
		if(adminLoginCheck.adminloginCheck(request, response)) {
			return null;
		}
		Object obj = joinPoint.proceed();  
		return obj; 
	}
}
