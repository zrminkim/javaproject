package shop.model.auction;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import shop.common.model.AuctionDto;
import shop.common.model.AuctionProgressDto;
import shop.controller.auction.AuctionUserBean;
import shop.controller.auction.AuctionUserDto;
import shop.controller.auction.PaginationAuction;

@Mapper
public interface AuctionDataMappingInterface {

	@Select("SELECT auction_no,  product_name, product_no, product_img, product_img_url, product_price, auction_open, auction_close, auction_sprice, TIMESTAMPDIFF(SECOND, now(),  auction_close) as TimeRemaining,userap_num, "
			+ "MAX(auctionp_price)  AS auctionp_price, auctionp_time, product_volume, product_level "
			+ "FROM auction JOIN product ON product_no = product_num left JOIN auction_progress ON auction_no= auction_num group by auction_no")
	List<AuctionUserDto> selectAutionList();

	@Select("SELECT auction_no,  product_name, product_no, product_img, product_img_url, product_price, auction_open, auction_close, auction_sprice, TIMESTAMPDIFF(SECOND, now(),  auction_close) as TimeRemaining,userap_num, auctionp_price, auctionp_time, product_volume, product_level \r\n"
			+ "			FROM auction JOIN product ON product_no = product_num left JOIN auction_progress ON auction_no= auction_num where auction_no = #{auction_no} ORDER BY auctionp_price DESC LIMIT 1")
	AuctionUserDto selectAutionOne(String auction_no);
	
	@Select("SELECT auctionp_no, user_no, auction_num, auctionp_price, auctionp_time, user_id, user_name "
			+ "FROM auction_progress  JOIN user ON user_no = userap_num WHERE auction_num = #{auction_no} ORDER BY auctionp_time DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<AuctionProgressDto> selectProgressList(PaginationAuction paginationAuction);
	
	@Select("select count(*) from auction_progress where auction_num = #{auction_no}")
	int getCountProgress(String auction_no);
	
	@Insert("insert into auction_progress(auction_num, userap_num, auctionp_price) value(#{auction_num},#{userap_num},#{auctionp_price})")
	boolean startBid(AuctionUserBean aBean);
	
}
