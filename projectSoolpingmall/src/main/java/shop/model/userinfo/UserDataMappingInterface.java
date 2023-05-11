package shop.model.userinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.common.model.UserDto;

@Mapper
public interface UserDataMappingInterface {
	@Select("select * from user where user_no = #{user_no}") // 해당유저 정보 받아오기
	UserDto selectUserInfo(String user_no);
	
	@Select("select count(*) from user where user_passwd = #{param2} and user_no=#{param1}") // 해당 유저 비밀번호 변경
	boolean checkPasswd(String no, String passwd);
	
	@Update("update USER set user_passwd = #{passwd} where user_no=#{no}") // 해당 유저 비밀번호 변경
	int updateUserPasswd(String passwd, String no);
	
	// 해당 유저 다른 정보 변경... 비번변경은... 진경이하면....
	@Update("update user set user_email = #{user_email}, user_tel = #{user_tel}, user_zipcode = #{user_zipcode}, user_addr = #{user_addr}, user_addr2 = #{user_addr2}")
	boolean modifyUserInfo(UserDto been);

	@Update("update user set user_cash = user_cash + #{cash} where user_no=#{no}") // 유저 캐시 증가
	int updateUserCash(String no, String cash);

	@Select("SELECT purchase_progress, count(*) as count FROM purchase WHERE user_num =#{no} GROUP BY purchase_progress")
	List<Map<String, String>> selectPurchaseProgress(String no);
	
	@Update("UPDATE user set user_cash = user_cash + #{param2} WHERE user_no = #{param1}")
	boolean chargeProgress(String no,String cash);
	
	
}