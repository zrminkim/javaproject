package shop.model.signup;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import shop.common.controller.UserBean;

@Mapper
public interface SignUpDataMapperInter {

	// 회원가입(회원정보 저장)
	@Insert("insert into user( user_id, user_name, user_passwd, user_birth, user_tel, user_email, user_zipcode, user_addr, user_addr2 )"
			+ " values( #{user_id}, #{user_name}, #{user_passwd}, #{user_birth}, #{user_tel}, #{user_email}, #{user_zipcode}, #{user_addr}, #{user_addr2} )")
	int InsertUser(UserBean userBean);

	// 아이디 중복검사
	@Select("select user_id from user where user_id=#{user_id}")
	String selectUserIdById(String user_id);
}
