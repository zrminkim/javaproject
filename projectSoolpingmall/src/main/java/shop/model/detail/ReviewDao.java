package shop.model.detail;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.controller.detail.ReviewPagination;

@Repository
public class ReviewDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DetailDataMappingInterface detailDataMappingInterface;
	
	// 리뷰 리스트 읽기
	public List<ReviewDto> listReview(ReviewPagination reviewpagination){
		List<ReviewDto> rlist = detailDataMappingInterface.reviewList(reviewpagination);
		//logger.info("rlist : " + rlist.size());
		return rlist;
	}

	// paging 처리
	public int getCount(String product_no) {
		int count = detailDataMappingInterface.getCount(product_no);
		return count;
	}
	
	// 리뷰 삭제
	public boolean deleteReview(String reply_no) {
		try{
			int re = detailDataMappingInterface.reviewDelete(reply_no);
			if(re > 0) return  true; else return false;
		} catch (Exception e) {
			logger.info("deleteReview : " + e);
			return false;
		}
	}

	// 개별 상품 리뷰
	public Map<String,Integer> replyStar(String product_no){
		Map<String,Integer> count = detailDataMappingInterface.StarCount(product_no);
		return count;
	}
}
