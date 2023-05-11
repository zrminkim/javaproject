package shop.model.userinfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.common.model.UserDto;
import shop.common.model.PurchaseDto;


@Repository
public class UserInfoDao {
	@Autowired
	private UserDataMappingInterface mappingInterface;
	
	public UserDto selectUserInfo(String user_no) {
		UserDto userDto = (UserDto) mappingInterface.selectUserInfo(user_no);
		return userDto;
	}
	
	public List<Map<String, String>> selectPurchaseProgress(String no){
		List<Map<String, String>> map = mappingInterface.selectPurchaseProgress(no);
		
		return map;
	}
	
	public boolean chargeProgress(String no, String cash){
		boolean b = mappingInterface.chargeProgress(no, cash);
		return b;
	}
	public String checkPasswd(String no, String passwd){
		boolean b = mappingInterface.checkPasswd(no, passwd);
		String a ="";
		System.out.println(no+"  "+passwd);
		System.out.println("b :"+b+" a : "+a );
		if(b) {
			a = "Y";
		}else {
			a = "N";
		}
		return a;
	}
	
	public boolean modifyUserInfo(UserDto been){
		boolean b = mappingInterface.modifyUserInfo(been);
		return b;
	}
	
	
}
