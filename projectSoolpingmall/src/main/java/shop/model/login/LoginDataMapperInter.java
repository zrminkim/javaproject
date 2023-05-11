package shop.model.login;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.common.model.UserDto;

@Mapper
public interface LoginDataMapperInter {

	// 아이디 찾기 : 이메일로 회원객체 찾기
	@Select("select user_id from user where user_email = #{param}")
	String selectUserIdByEmail(String user_email);

	// 비밀번호 찾기 : id, 이메일로 회원객체 찾기
	@Select("select * from user where user_id=#{param1} and user_email=#{param2}")
	UserDto selectUserByIdAndEmail(String user_id, String user_email);

	// 임시비밀번호를 유저 비밀번호로 갱신
	@Update("update user set user_passwd=#{param1} where user_no=#{param2}")
	int updatePasswd(String user_passwd, int user_no);

	// 로그인 : id, passwd로 회원객체 찾기(state=y=사용자)
	@Select("select * from user where user_id=#{param1} and user_passwd=#{param2} and user_state='Y'")
	UserDto selectUserByIdAndPasswd(String user_id, String user_passwd);

	// @Select("select * from USER where user_id=#{username}")
	// UserDto selectUserByUserId(String user_id);

	// 카카오 자동회원가입
	@Insert("insert into user(user_id,user_email,user_auth) values(#{param1},#{param2},'K')")
	int insertKakaoUserInfo(String user_id, String user_email);

	// 구글 자동회원가입
	@Insert("insert into user(user_email,user_auth) values(#{param1},'G')")
	int insertGoogleUserInfo(String user_email);

	// 구글 회원 조회(복수 가능)
	@Select("select * from user where user_email = #{param}")
	UserDto selectUserByEmail(String user_email);
}
