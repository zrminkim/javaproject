package shop.controller.admin;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserBean {

	private int user_no, user_cash, user_point;
	private String user_id, user_name,user_passwd, user_email, user_gen, user_birth, user_tel, user_zipcode, user_addr, user_addr2,
			user_auth,user_state;
	private LocalDate user_regdate, user_moddate;
	private String searchUser, searchValue;
}
