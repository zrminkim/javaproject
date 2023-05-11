package shop.controller.admin;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductBean {
	private String  product_name, product_name_eng, product_img, product_img_url, product_cntr,product_type,product_regdate, product_moddate;
	private int product_no, product_price, product_volume, product_stock, product_level;
	private MultipartFile uploadfile;
	private String searchProduct, searchValue;
	
	
}
