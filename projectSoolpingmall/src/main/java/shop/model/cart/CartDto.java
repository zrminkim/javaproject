package shop.model.cart;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CartDto {	// 장바구니 페이지에 필요한 순수 데이터 객체

	private int cart_no, user_num, product_num, cart_cnt, product_price, total, user_no, product_no;
	private String product_name, user_name, product_img, product_img_url;
	private LocalDate cart_regdate, cart_moddate;
}
