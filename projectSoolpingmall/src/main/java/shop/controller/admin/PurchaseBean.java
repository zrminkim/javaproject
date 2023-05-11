package shop.controller.admin;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PurchaseBean {

	private String user_name, purchase_rec, purchase_zipcode, purchase_addr1, 
	purchase_addr2, purchase_tel, purchase_email, purchase_req, purchase_progress;
	private int purchase_no, user_num, purchase_total, purchase_upoint;
	private LocalDate purchase_regdate, purchase_moddate;
	
	private String searchPurchase, searchValue;
	
}
