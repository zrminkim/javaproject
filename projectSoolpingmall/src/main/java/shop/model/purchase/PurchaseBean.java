package shop.model.purchase;



import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PurchaseBean {
	private String user_name, purchase_rec, purchase_zipcode, purchase_addr1, purchase_addr2, purchase_tel,
			purchase_email, purchase_req, purchase_progress, purchase_orderno, purchase_num, product_name, product_img;
	private int purchase_no, user_num, purchase_total, purchase_upoint, product_num, product_cnt, product_price;
	private Date purchase_regdate, purchase_moddate, productl_regdate;

}
