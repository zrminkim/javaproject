package shop.model.userhistory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.controller.userhistory.PaginationUser;

@Mapper
public interface UserhDataMappingInterface {

	@Select("select count(*) from purchase where user_num = #{user_no}")
	int getCountPurchase(String user_no);

	@Select("select * from purchase_list join purchase on purchase_no = purchase_num JOIN product ON product_no = product_num where user_num = #{user_no} ORDER BY purchase_regdate DESC LIMIT #{rowCount} offset #{offset}")
	List<PurchaseDto> selectPurchaseList(PaginationUser paginationUser);
	
	@Select("select count(*) from wishlist where user_num = #{user_no}")
	int getCountProduct(String user_no);
	
	@Select("SELECT * FROM wishlist JOIN product ON product_no = product_num WHERE user_num = #{user_no} ORDER BY wishlist_regdate DESC LIMIT #{rowCount} offset #{offset}")
	List<ProductDto> userWishList(PaginationUser paginationUser);
	
	@Select("select count(*) from product_reply where user_num = #{user_no}")
	int getCountReply(String user_no);
	
	@Select("SELECT * FROM product_reply JOIN product ON product_no = product_num WHERE user_num = #{user_no} ORDER BY reply_regdate DESC LIMIT #{rowCount} offset #{offset}")
	List<UserReplyDto> userRelpyList(PaginationUser paginationUser);
	
}
