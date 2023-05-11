package shop.model.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import shop.common.controller.UserBean;
import shop.controller.signup.HashingPasswd;

@Repository
@Slf4j
public class SignUpDao {
	
	
	@Autowired
	private SignUpDataMapperInter mapperInter;
	

	// 회원가입(회원정보 저장)
	public boolean insertUser(UserBean userBean) {
		try {
			int re = mapperInter.InsertUser(userBean);
			if(re > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.info("insert fail :" + e.getMessage());
			return false;
		}
	}
	
	// 아이디 중복 확인
	public boolean selectUserIdById(String user_id) {
		boolean b = false; // 중복된 아이디가 없으면 false
		String userId = mapperInter.selectUserIdById(user_id);
		if(userId!=null) { // 중복된 아이디를 찾으면 true
			b = true;
		}
		return b;
	}
	
	

	
	
}
