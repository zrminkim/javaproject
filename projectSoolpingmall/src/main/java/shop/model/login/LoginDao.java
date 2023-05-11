package shop.model.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.common.model.UserDto;

// 로그인, 아이디 찾기, 비밀번호 찾기용 레포지토리

@Repository
public class LoginDao {

	
	@Autowired
	private LoginDataMapperInter mapperInter;
	
	// 로그인 : 조회된 객체를 반환
	public UserDto selectUserByIdAndPasswd(String user_id, String user_passwd) {
		UserDto dto = mapperInter.selectUserByIdAndPasswd(user_id, user_passwd);
		return dto;
	}
	
	
	
	
	
	
	// 아이디 찾기 : 조회된 id 반환
	
	public String selectUserIdByEmail(String user_email) {
		String user_id =  mapperInter.selectUserIdByEmail(user_email);
		return user_id;
	}
	
	// 비밀번호 찾기 : 조회된 객체를 반환
	public UserDto selectUserByIdAndEmail(String user_id,String user_email) {
		UserDto dto =  mapperInter.selectUserByIdAndEmail(user_id, user_email);
		return dto;
	}
	
	// 임시 비밀번호로 비밀번호 갱신
	public boolean updatePasswd(String user_passwd,int user_no) {
		boolean b = false; // 갱신 실패하면 false 반환
		int re = mapperInter.updatePasswd(user_passwd, user_no);
		if(re>0) { // 갱신 성공하면 true 반환
			b = true;
		}
		return b;
	}
	
	/*
	public UserDto selectUserByUserId(String user_id) {
		UserDto user = mapperInter.selectUserByUserId(user_id);
		return user;
	}
	*/

	
	public boolean insertKakaoUserInfo(String user_id, String user_email) {
		boolean b = false;
		int re = mapperInter.insertKakaoUserInfo(user_id, user_email);
		if(re>0)  b = true;
		return b;
	} 
	public boolean insertGoogleUserInfo(String user_email) {
		boolean b = false;
		int re = mapperInter.insertGoogleUserInfo(user_email);
		if(re>0)  b = true;
		return b;
	} 

	public UserDto selectUserByEmail(String user_email) {
		UserDto  user =  mapperInter.selectUserByEmail(user_email);
		return user;
	}
}
