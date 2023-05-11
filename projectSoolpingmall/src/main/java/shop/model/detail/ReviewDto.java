package shop.model.detail;

import lombok.Data;

@Data
public class ReviewDto {

	private int reply_no, user_num, product_num, reply_eval;
	
	private String reply_comment, reply_regdate, user_name;
}
