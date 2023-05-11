package shop.common.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AuctionDto {
	private int auction_no,user_num,product_num,auction_sprice,auction_successbid;
	private String auction_state,auction_open,auction_close;
	private LocalDate auction_regdate, auction_moddate;
	
}
