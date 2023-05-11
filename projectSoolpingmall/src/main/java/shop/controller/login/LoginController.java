package shop.controller.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import groovy.lang.GrabExclude;
import shop.common.controller.UserBean;
import shop.common.model.UserDto;
import shop.controller.signup.HashingPasswd;
import shop.model.login.GoogleProfile;
import shop.model.login.KakaoProfile;
import shop.model.login.LoginDao;
import shop.model.login.OAuthToken;
import shop.model.login.OAuthToken2;
import shop.myaspect.login.UserLoginCheck;

@Controller
public class LoginController {

	private LoginDao loginDao;
	private HashingPasswd hashingPasswd;

	@Autowired
	public LoginController(LoginDao loginDao, UserLoginCheck loginCheck, HashingPasswd hashingPasswd) {
		this.loginDao = loginDao; // repository
		this.hashingPasswd = hashingPasswd; // 패스워드 해싱 메소드
	}

	/*
	 * login 요청을 받으면 세션 검사 실시
	 * 세션이 있으면 메인페이지로 이동
	 * 세션이 없으면 로그인 페이지로 이동
	 */
	@GetMapping("login")
	public String getLogin() throws Exception {

		return "login/login"; // 세션이 없는 경우(로그인 페이지로)
	}

	/*
	 * 입력받는 passwd를 해싱하고
	 * id와 해싱한 passwd를 통하여 일치하는 회원을 조회
	 * 조회되는 회원이 있으면 세션 등록
	 * 조회되는 회원이 없으면 로그인페이지에서 알림문구를 띄움
	 */
	@PostMapping("login")
	public String postLogin(@ModelAttribute UserBean userBean, HttpServletRequest request, Model model)
			throws Exception {

		// 입력받은 비밀번호를 해싱
		String hashedPasswd = hashingPasswd.getHashValue(userBean.getUser_passwd());

		// 입력받은 id,pwd(해싱완료)로 회원 조회
		UserDto dto = loginDao.selectUserByIdAndPasswd(userBean.getUser_id(), hashedPasswd);

		if (dto != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user_no", dto.getUser_no()); // 세션 등록
			/*
			 * System.out.println("referee after login: "+request.getHeader("Referer"));
			 * if(request.getHeader("Referer") != null) { // 다른 작업을 하다가 온 경우
			 * if(request.getHeader("Referer")=="/login") {
			 * return "redirect:/";
			 * } else {
			 * return "redirect:" + request.getHeader("Referer"); // 직전 페이지로 이동
			 * }
			 * } else { // 메인페이지에서 바로 온 경우
			 * return "redirect:/"; // 메인페이지로 이동
			 * }
			 */
			return "redirect:/"; // 일단 메인페이지로

		} else { // 회원이 없으면 로그인 페이지에 알림문구
			String msg = "아이디 또는 비밀번호를 확인하세요.";
			model.addAttribute("data", msg);
			return "login/login";
		}

	}

	/*
	 * logout 요청을 받으면 세션 삭제, 메인페이지로 이동
	 */
	@GetMapping("logout")
	public String getLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // 세션이 없으면 생성X, 있으면 사용
		if (session != null) {
			session.removeAttribute("user_no");
			System.out.println("(defalut)session.getId() : " + session.getId());
		}
		return "redirect:/";

	}

	/*
	 * 카카오 로그인
	 * 1. code 요청 -> 인증 성공 시 code를 응답받음
	 * 2. 받은 code를 통하여 access token 요청-> access token을 포함한 응답받음
	 * 2. 액세스 토큰으로 사용자 정보 요청-> 응답받음
	 */

	@GetMapping("/auth/kakao")
	public String kakaoLogin(HttpServletRequest request, String code, HttpSession session) throws Exception {
		// kakaoProfile 객체를 얻기
		System.out.println("code : " + code);
		OAuthToken oAuthToken = getOAuthToken(code);
		System.out.println("토큰 : " + oAuthToken);
		KakaoProfile kakaoProfile = getKakaoProfile(oAuthToken);
		System.out.println("포로필 : " + kakaoProfile);

		// kakaoProfile에서 꺼낸 고객 정보
		String kakao_id = Long.toString(kakaoProfile.getId());
		String kakao_email = kakaoProfile.getKakao_account().getEmail();
		String kakao_gender = kakaoProfile.getKakao_account().getGender().substring(1, 1);
		System.out.println("kakao_gender : " + kakao_gender);
		// 술핑몰 기존 회원인지 조회
		UserDto dto = loginDao.selectUserByIdAndEmail(kakao_id, kakao_email);
		if (dto == null) { // 처음으로 카카오 로그인하는 경우 : 자동 회원가입######
			loginDao.insertKakaoUserInfo(kakao_id, kakao_email);
		} // 술핑몰 데이터베이스에 이미 회원정보가 있으면 저장X

		// 토큰을 세션에 저장(로그아웃용)
		session.setAttribute("user_no", oAuthToken.getAccess_token());
		System.out.println("카카오 로그인 성공 :" + session.getAttribute("user_no"));

		return "redirect:/"; // 일단 메인페이지로

		/*
		 * if(request.getHeader("Referer") != null) { // 다른 작업을 하다가 온 경우
		 * return "redirect:" + request.getHeader("Referer"); // 직전 페이지로 이동
		 * 
		 * } else { // 메인페이지에서 바로 온 경우
		 * return "redirect:/"; // 메인페이지로 이동
		 * }
		 */

	}

	public OAuthToken getOAuthToken(String code) throws Exception {

		RestTemplate template = new RestTemplate();
		// HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// HttpBody 객체 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "c2b5e019d5554c9e47d42e285de7f080");
		params.add("redirect_uri", "http://localhost/auth/kakao");
		params.add("code", code);
		// 헤더와 바디를 HttpEntity에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// access token 요청 하기(post 방식, response 받음)
		ResponseEntity<String> response = template.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);

		// 받은 응답(json형식 데이터)을 ObjectMaapper에 담음
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {

			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// System.out.println("액세스 토근 : "+oAuthToken.getAccess_token());

		return oAuthToken;
	}

	public KakaoProfile getKakaoProfile(OAuthToken oAuthToken) throws Exception {
		RestTemplate template = new RestTemplate();

		// HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 헤더와 바디를 HttpEntity에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

		// 사용자정보 요청 하기(post 방식, response 받음)
		ResponseEntity<String> response = template.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		System.out.println(response.getBody()); // 나옴

		// ObjectMaapper에 response를 담음(json형식 데이터)
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(kakaoProfile);

		return kakaoProfile;

	}

	@GetMapping("kakao/logout")

	// 1. 액세스 토큰을 만료시키는 것. 즉, 더이상 술핑몰 서버가 카카오 서버에 사용자 정보를 받을 수 없음
	// 2. 술핑몰 서버의 세션 만료
	public String kakaoLogout(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션이 없으면 생성X, 있으면 사용
		session = request.getSession(false);

		// 카카오로그인 한 경우
		if (session.getAttribute("user_no") != null) {
			session.removeAttribute("user_no");

			System.out.println("(kakao)session.getId() : " + session.getId());
		}

		RestTemplate template = new RestTemplate();
		if (session.getAttribute("user_no") != null) { // 토큰이 유효하면
			// HttpHeader 객체 생성
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + session.getAttribute("user_no"));

			// 헤더와 바디를 HttpEntity에 담기
			HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);

			// 사용자정보 요청 하기(post 방식, response 받음)
			ResponseEntity<String> response = template.exchange(
					"https://kapi.kakao.com/v1/user/logout",
					HttpMethod.POST,
					kakaoLogoutRequest,
					String.class);
		}

		System.out.println("카카오 로그아웃 성공 : " + session.getAttribute("user_no"));

		return "redirect:/";
	}

	// 구글 로그인
	@GetMapping("/auth/google")
	public String googleLogin(HttpServletRequest request, String code, HttpSession session) throws Exception {
		// googleProfile 객체를 얻기
		System.out.println("code : " + code);
		OAuthToken2 oAuthToken2 = getOAuthToken2(code);
		// System.out.println("토큰 : "+oAuthToken2);

		GoogleProfile googleProfile = getGoogleProfile(oAuthToken2);
		// System.out.println("포로필 : "+googleProfile);

		// kakaoProfile에서 꺼낸 고객 정보

		String google_name = googleProfile.getName(); // null 가능
		String google_email = googleProfile.getEmail();

		// 술핑몰 기존 회원인지 조회
		// ##### 이메일은 중복가능하니 이메일로만 조회하는 것은 좋은 방법이 아닌듯 변경필요############
		UserDto user = loginDao.selectUserByEmail(google_email); // 이메일만 확정적으로 들어와서 검색에 활용
		if (user == null) { // 처음으로 구글 로그인하는 경우 : 자동 회원가입
			loginDao.insertGoogleUserInfo(google_email);
		} // 술핑몰 데이터베이스에 이미 회원정보가 있으면 저장X

		// 토큰을 세션에 저장(로그아웃용)
		session.setAttribute("user_no", oAuthToken2.getAccess_token());
		return "redirect:/"; // 일단 메인페이지로

		/*
		 * if(request.getHeader("Referer") != null) { // 다른 작업을 하다가 온 경우
		 * return "redirect:" + request.getHeader("Referer"); // 직전 페이지로 이동
		 * 
		 * } else { // 메인페이지에서 바로 온 경우
		 * return "redirect:/"; // 메인페이지로 이동
		 * }
		 */
	}

	public OAuthToken2 getOAuthToken2(String code) throws Exception {

		RestTemplate template = new RestTemplate();
		// HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// HttpBody 객체 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", "633724080548-1kq9fljlhhtan0dtm8mufpptotu41688.apps.googleusercontent.com");
		params.add("client_secret", "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");
		params.add("redirect_uri", "http://localhost/auth/google");
		params.add("grant_type", "authorization_code");
		// 헤더와 바디를 HttpEntity에 담기
		HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(params, headers);

		// access token 요청 하기(post 방식, response 받음)
		ResponseEntity<String> response = template.exchange(
				"https://oauth2.googleapis.com/token",
				HttpMethod.POST,
				googleTokenRequest,
				String.class);

		// 받은 응답(json형식 데이터)을 ObjectMaapper에 담음
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken2 oAuthToken2 = null;
		try {

			oAuthToken2 = objectMapper.readValue(response.getBody(), OAuthToken2.class);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// System.out.println("액세스 토근 : "+oAuthToken2.getAccess_token());

		return oAuthToken2;
	}

	public GoogleProfile getGoogleProfile(OAuthToken2 oAuthToken2) throws Exception {
		RestTemplate template = new RestTemplate();

		// HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oAuthToken2.getAccess_token());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 헤더와 바디를 HttpEntity에 담기
		HttpEntity<MultiValueMap<String, String>> googleProfileRequest = new HttpEntity<>(headers);

		// 사용자정보 요청 하기(post 방식, response 받음)
		ResponseEntity<String> response = template.exchange(
				"https://oauth2.googleapis.com/tokeninfo",
				HttpMethod.POST,
				googleProfileRequest,
				String.class);
		// System.out.println(response.getBody()); // 나옴

		// ObjectMaapper에 response를 담음(json형식 데이터)
		ObjectMapper objectMapper = new ObjectMapper();
		GoogleProfile googleProfile = null;
		try {
			googleProfile = objectMapper.readValue(response.getBody(), GoogleProfile.class);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// System.out.println(googleProfile);

		return googleProfile;

	}

	@GetMapping("/google/logout")
	public String googleLogout(HttpSession session, HttpServletRequest request) throws Exception {

		// RestTemplate template = new RestTemplate();

		// 세션이 없으면 생성X, 있으면 사용
		session = request.getSession(false);

		if (session.getAttribute("user_no") != null) { // 토큰이 유효하면
			/*
			 * 구글은 로그아웃 api 요청명이 안나옴;;
			 * 일단 술핑몰의 세션만 제거하도록 함
			 * 
			 * // HttpHeader 객체 생성
			 * HttpHeaders headers = new HttpHeaders();
			 * headers.add("Authorization", "Bearer "+session.getAttribute("user_no"));
			 * 
			 * 
			 * // 헤더와 바디를 HttpEntity에 담기
			 * HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest =
			 * new HttpEntity<>(headers);
			 * 
			 * 
			 * // 사용자정보 요청 하기(post 방식, response 받음)
			 * ResponseEntity<String> response = template.exchange(
			 * "https://kapi.kakao.com/v1/user/logout",
			 * HttpMethod.POST,
			 * kakaoLogoutRequest,
			 * String.class
			 * );
			 * 
			 * System.out.println("response: "+response.getBody()); //
			 */
		}

		if (session.getAttribute("user_no") != null) {
			session.removeAttribute("user_no");
			System.out.println("(google)session.getId() : " + session.getId());
			System.out.println("google session : " + session.getAttribute("user_no"));
		}

		return "redirect:/";

	}

}
