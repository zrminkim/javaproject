package shop.model.userhistory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.controller.userhistory.PaginationUser;


@Repository
public class UserHistoryDao {
	@Autowired
	private UserhDataMappingInterface mappingInterface;
	
	public int getCountPurchase(String user_no) {
		int count = mappingInterface.getCountPurchase(user_no);
		return count;
	}
	
	public List<PurchaseDto> selectPurchaseList(PaginationUser paginationUser) {
		List<PurchaseDto> list = mappingInterface.selectPurchaseList(paginationUser);
		return list;
	}

	public int getCountProduct(String user_no) {
		int count = mappingInterface.getCountProduct(user_no);
		return count;
	}

	public List<ProductDto> userWishList(PaginationUser paginationUser) {
		List<ProductDto> list = mappingInterface.userWishList(paginationUser);
		return list;
	}

	public int getCountReply(String user_no) {
		int count = mappingInterface.getCountReply(user_no);
		return count;
	}

	public List<UserReplyDto> userRelpyList(PaginationUser paginationUser) {
		List<UserReplyDto> list = mappingInterface.userRelpyList(paginationUser);
		return list;
	}

	
}
