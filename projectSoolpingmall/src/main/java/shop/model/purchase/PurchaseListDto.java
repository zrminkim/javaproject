package shop.model.purchase;

import lombok.Data;

@Data
public class PurchaseListDto {
	private int product_num, product_cnt;
	private String purchase_num;
}
