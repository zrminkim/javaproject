package shop.model.auction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.common.model.AuctionDto;
import shop.common.model.AuctionProgressDto;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.controller.auction.AuctionUserBean;
import shop.controller.auction.AuctionUserDto;
import shop.controller.auction.PaginationAuction;
import shop.controller.userhistory.PaginationUser;


@Repository
public class AuctionDao {
	@Autowired
	private AuctionDataMappingInterface mappingInterface;
	
	public List<AuctionUserDto> selectAutionList() {
		List<AuctionUserDto> list = mappingInterface.selectAutionList();
		return list;
	}
	
	public AuctionUserDto selectAutionOne(String auction_no) {
		AuctionUserDto dto = mappingInterface.selectAutionOne(auction_no);
		return dto;
	}

	public List<AuctionProgressDto> selectProgressList(PaginationAuction paginationAuction) {
		List<AuctionProgressDto> list = mappingInterface.selectProgressList(paginationAuction);
		return list;
	}
	
	public int getCountProgress(String auction_no) {
		int count = mappingInterface.getCountProgress(auction_no);
		return count;
	}
	
	public boolean startBid(AuctionUserBean aBean) {
		return mappingInterface.startBid(aBean);
	}
	
	
}
