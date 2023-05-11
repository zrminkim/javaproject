package shop.model.userhistory;

import lombok.Data;

@Data
public class UserWishListDto {
	private String product_name, product_name_eng, product_img, product_img_url, product_cntr,product_type,product_regdate, product_moddate, 
		product_no, product_price,product_volume, product_stock, product_level;
	private String review_no, review_id, review_cont, review_date;	
}
