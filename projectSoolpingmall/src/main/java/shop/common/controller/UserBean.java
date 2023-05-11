package shop.common.controller;

import lombok.Data;

@Data
public class UserBean {
	private String user_name,user_id,user_passwd,
	user_email,user_gen,
	user_birth,user_tel,
	user_zipcode,user_addr,user_addr2,
	user_auth,user_state,
	user_regdate,user_moddate;
	private int user_no,user_cash,user_point;
	

	
}
