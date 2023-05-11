package shop.controller.detail;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DetailBean {

	public String product_name, product_name_eng, product_img, product_img_url, product_type, product_cntr;
	// 상품 이름, 영어이름, 이미지, 이미지url(?), 종류, 국가
	public String product_no, product_price, product_volume, product_stock, product_level;
	// 상품 번호, 가격, 용량, 수량, 도수
	public String product_regdate, product_moddate;
	// 생성일, 수정일
	public String wishlist_no, user_num, product_num;
	// 찜하기 번호, 찜하기 유저 번호, 찜하기 상품 번호
	public String user_id, user_name, user_passwd;
	// 유저 아이디, 이름
	public String user_no, user_point;
	// 유저 번호, 포인트
	
	private String reply_no, reply_eval, reply_comment, reply_regdate;
	// 리뷰 번호, 아이디, 내용, 생성일
}
