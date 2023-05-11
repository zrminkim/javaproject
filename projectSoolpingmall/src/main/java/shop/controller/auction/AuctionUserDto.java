package shop.controller.auction;

import java.util.Date;

import lombok.Data;

@Data
public class AuctionUserDto {
	private String auction_no, user_num, auction_sprice, auctionp_time, TimeRemaining;
	private Date auction_open, auction_close; // ACTION
	
	private String userap_num, auctionp_price; // AUCTION_PROGRESS
	private String user_id; //USER
	private String product_name, product_no, product_img, product_img_url, product_price, product_volume, product_level; // PRODUCT
	
}
