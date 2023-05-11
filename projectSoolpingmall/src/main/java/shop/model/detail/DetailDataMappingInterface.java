package shop.model.detail;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.controller.detail.ReviewPagination;

@Mapper
public interface DetailDataMappingInterface {
	
	// 상품 정보 가져오기
	@Select("select * from product where product_no=#{product_no}")
	DetailDto DetailListAll(String product_no);
	
	// 찜하기 확인 
	@Select("select count(*) from wishlist where product_num=#{param1} and user_num=#{param2}")
	boolean wishlist(String product_no,String user_no);
	
	// 찜하기 활성화
	@Insert("insert into wishlist (user_num, product_num) values(#{user_no}, #{product_no})")
	boolean wishIn(@Param("user_no")String user_no,@Param("product_no")String product_no);
	
	// 찜하기 비활성화
	@Insert("delete from wishlist where user_num=#{user_no} and product_num=#{product_no}")
	boolean wishOut(@Param("user_no")String user_no,@Param("product_no")String product_no);
	
	// 리뷰 불러오기
	@Select("select reply_no, user_num, product_num, user_name, reply_eval, reply_comment, date_format(reply_regdate, '%y-%m-%d %H:%i')	as reply_regdate from user join product_reply on user_num = user_no where product_num=#{product_no} order by reply_no desc limit #{rowCount} offset #{offset}")
	List<ReviewDto> reviewList(ReviewPagination reviewpagination);
	
	// 리뷰 삭제
	@Delete("delete from product_reply where reply_no=#{reply_no}")
	int reviewDelete(String reply_no);

	// paging 처리용
	@Select("select count(*) from product_reply where product_num=#{product_num}")
	int getCount(String product_no);
	
	// 개별 상품 별 갯수 불러오기
	@Select("select count(*) as count, floor(avg(reply_eval) * 2) as reply_no from product_reply where product_num =#{prodcut_num} group by product_num;")
	Map<String,Integer> StarCount(String reply_num);

	// 조회수 올리기
	@Update("update product set product_views=product_views+1 where product_no=#{product_no}")
	boolean updateViews(String product_no);

}
