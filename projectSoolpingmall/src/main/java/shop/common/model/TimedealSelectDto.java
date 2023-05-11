package shop.common.model;



import lombok.Data;

@Data
public class TimedealSelectDto {
	private int product_no,product_price, discount, timedeal_discnt;
	private String product_type, product_name, product_img;
}
