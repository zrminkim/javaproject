package shop.common.model;

import java.util.Date;

import lombok.Data;

@Data
public class AuctionProgressDto {
	private String auctionp_no, product_num, user_no, auction_num, auctionp_price;
	private Date auctionp_time; 
	
	private String user_id, user_name;
}
