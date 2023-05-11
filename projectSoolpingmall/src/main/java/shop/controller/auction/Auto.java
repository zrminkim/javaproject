package shop.controller.auction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import shop.model.auction.AuctionDao;

@Service
public class Auto{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuctionDao auction;
	
	public Auto(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Scheduled(cron = "0 0 * * * *")
	public void runQuery() {
		List<AuctionUserDto> auctionUserDto = auction.selectAutionList();
		for (AuctionUserDto auction : auctionUserDto) {
		    System.out.println("Name: " + auction.getAuction_close());
		    
		}

	}
}
