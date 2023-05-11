package shop.myaspect.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserLoginAdvice {

	@Autowired
	private UserLoginCheck loginCheck;

	// pointcut 여러개를 ||로 병기!
	// 결제, 장바구니, 마이페이지 등
	// UserInfoController
	// UserHistroyController
	// PurchaseController
	// CartController
	//예외처리를 try catch로 하지 말 것!
	//받는 메소드에서 request,response 넣어줄 것
	@Around("execution(* shop.controller.userinfo.UserInfoController.*(..)) || execution(* shop.controller.userhistory.UserHistroyController.*(..)) || execution(* shop.controller.purchase.PurchaseController.*(..)) || execution(* shop.controller.cart.CartController.*(..))")
	public Object aopProcess(ProceedingJoinPoint joinPoint) throws Throwable {

		// request, response는 joinPoint에서 가져오기
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		for (Object obj : joinPoint.getArgs()) {
			if (obj instanceof HttpServletRequest) {
				request = (HttpServletRequest) obj;
			}
			if (obj instanceof HttpServletResponse) {
				response = (HttpServletResponse) obj;
			}
		}

		// 세션이 있는지 검사
		// 세션이 없는 경우(false) : 핵심 메소드 수행 안함
		// 세션이 있는 경우(true) : 핵심 메소드 수행
		if (loginCheck.checkUserSession(request, response)) {
			return null;
		}

		Object object = joinPoint.proceed();

		return object;
	}
}
