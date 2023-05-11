package shop.common.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PurchaseDto { // 결제하기 페이지에 필요한 순수 데이터 객체
	private String user_name, purchase_rec, purchase_zipcode, purchase_addr1, purchase_addr2, purchase_tel,
			purchase_email, purchase_req, purchase_progress;
	private String product_name, product_img, product_no, product_type, productl_regdate, product_price, product_cnt;
	private int purchase_no, user_num, purchase_total, purchase_upoint;
	private String purchase_regdate, purchase_moddate;
}
