package shop.model.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.common.model.AuctionDto;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.QnaDto;
import shop.common.model.TimedealDto;
import shop.common.model.UserDto;
import shop.controller.admin.AuctionBean;
import shop.controller.admin.Pagination;
import shop.controller.admin.ProductBean;
import shop.controller.admin.PurchaseBean;
import shop.controller.admin.QnaBean;
import shop.controller.admin.TimeDealBean;
import shop.controller.admin.UserBean;

@Mapper
public interface DataMappingInterface {

	@Select("select admin_id, admin_passwd from admin where admin_id = #{param1} and admin_passwd = #{param2}")
	AdminDto getAdminData(String id, String passwd);
	//제품 관련
	// 제품 전체 수 출력
	@Select("select * from product")
	List<ProductDto> getProductAll();
	
	//고객 전체 수 출력
	@Select("select * from user")
	List<UserDto> getUserAll();
	
	//제품 페이징처리 메소드
	@Select("select * from product ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> getProductPageAll(Pagination pagination);
	
	// 고객 페이징처리 메소드
	@Select("select * from user ORDER BY user_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<UserDto> getUserPageAll(Pagination pagination);
	
	// QnA 페이징처리 메소드
	@Select("select * from qna ORDER BY qna_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<QnaDto> getQnaPageAll(Pagination pagination);
	
	// Purchase 페이징처리 메소드
	@Select("select * from purchase ORDER BY purchase_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<PurchaseDto> getPruchasePageAll(Pagination pagination);
	// 제품 전체 값 출력--------------------------------
	@Select("select count(*) from product")
	int getCount();
	
	@Select("select count(*) from user")
	int getUserCount();
	
	@Select("select count(*) from qna")
	int getQnaCount();
	
	@Select("select count(*) from purchase")
	int getPurchaseCount();
	
	@Select("select count(*) from auction")
	int getAuctionCount();
	
	@Select("select count(*) from timedeal")
	int getTimedealCount();
	
	@Select("SELECT distinct product_cntr FROM product")
	List<String> showProduct_cntr();
	
	@Select("SELECT distinct product_type FROM product")
	List<String> showProduct_type();
	//----------------------------------------------
	@Select("select max(product_no) from product")
	int product_noCnt();
	
	// 종류 및 나라 검색에 맞는 테이블의 내용들 불러오기--------------
	@Select("select * from product where ${searchProduct} like concat('%',#{searchValue},'%') ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> detailSearch(Pagination pagination);
	
	@Select("select * from user where ${searchUser} like concat('%',#{searchValue},'%') ORDER BY user_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<UserDto> detailUserSearch(Pagination pagination);
	
	@Select("select * from qna where ${searchSubject} like concat('%',#{searchValue},'%') ORDER BY qna_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<QnaDto> detailQnaSearch(Pagination pagination);
	
	@Select("select * from purchase where ${searchPurchase} like concat('%',#{searchValue},'%') ORDER BY purchase_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<PurchaseDto> detailPurchaseSearch(Pagination pagination);
	//----------------------------------------------
	
	@Select("select count(*) from product where ${searchProduct} like concat('%',#{searchValue},'%')")
	int getDatailCount(ProductBean bean);
	
	@Select("select count(*) from user where ${searchUser} like concat('%',#{searchValue},'%')")
	int getUserDatailCount(UserBean bean);
	
	@Select("select count(*) from qna where ${searchSubject} like concat('%',#{searchValue},'%')")
	int getQnaDatailCount(QnaBean bean);
	
	@Select("select count(*) from purchase where ${searchPurchase} like concat('%',#{searchValue},'%')")
	int getPruchaseDatailCount(PurchaseBean bean);
	
	// 제품 수정 할 제품의 상세 정보 가져오기
	@Select("select * from product where product_no = #{param1}")
	ProductDto searchProduct(int no);
	
	// 경매 상품 수정 할 제품의 상세 정보 가져오기
	@Select("select * from auction where auction_no = #{param1}")
	AuctionDto searchAuction(int no);
	
	// 타임딜 상품 수정 할 제품의 상세 정보 가져오기
	@Select("select * from timedeal where product_no = #{param1}")
	TimedealDto searchTimedeal(int no);
	// 제품 등록
	@Insert("INSERT INTO product(product_name, product_name_eng, product_img, product_img_url"
			+ ", product_price, product_type, product_cntr, product_volume, product_stock, product_level) VALUES(#{product_name},#{product_name_eng},#{product_img},#{product_img_url}"
			+ ",#{product_price},#{product_type},#{product_cntr},#{product_volume},#{product_stock},#{product_level})")
	int inserProduct(ProductBean bean);
	
	@Update("update product set product_name=#{product_name},product_name_eng = #{product_name_eng}, product_img = #{product_img}, product_img_url = #{product_img_url},"
			+ "product_price = #{product_price},product_type = #{product_type}, product_cntr=#{product_cntr},product_volume=#{product_volume},product_stock=#{product_stock},"
			+ "product_level=#{product_level},product_moddate=now() where product_no= #{product_no}")
	int updateProduct(ProductBean bean);
	
	@Update("UPDATE user SET user_state = case when user_state = 'Y' then 'N'"
			+ "when user_state = 'N' then 'Y' ELSE 'N' END WHERE user_no = #{user_no}")
	int updateUserState(int user_no);
	
	@Update("UPDATE qna SET qna_secret = case when qna_secret = 'S' then 'U' "
			+ "when qna_secret = 'U' then 'S' ELSE 'U' END WHERE qna_no = #{qna_no};")
	int updateQnasecret(int qna_no);
	
	@Update("UPDATE qna SET qna_rep = case when qna_rep = 'N' then 'Y' "
			+ "when qna_rep = 'Y' then 'N' ELSE 'N' END WHERE qna_no = #{qna_no};")
	int updateQnaReply(int qna_no);
	
	// 주문관리 배송 처리 변경
	@Update("UPDATE purchase SET purchase_progress = case when purchase_progress = 'P' then 'D' "
			+ "when purchase_progress = 'D' then 'C' ELSE 'P' END WHERE purchase_no = #{purchase_no};")
	int updatePurchaseProgress(int qna_no);
	
	@Update("UPDATE auction SET  auction_open = STR_TO_DATE(#{auction_open},'%Y-%m-%d %H:%i:%s'), auction_close = STR_TO_DATE(#{auction_close},'%Y-%m-%d %H:%i:%s'), auction_sprice = #{auction_sprice} WHERE auction_no = #{auction_no}")
	int updateAuction(AuctionBean bean);
	
	@Delete("delete from product where product_no=#{product_no}")
	int deleteProduct(int no);
	
	
	// 차트 관련
	// 기간별 결제 총액
	@Select("SELECT DATE(purchase_regdate) as purchase_regdate, SUM(purchase_total) as purchase_total"
			+ " FROM purchase"
			+ " WHERE purchase_progress = 'C'"
			+ " GROUP BY DATE(purchase_regdate)")
	List<PurchaseDto> getPurchaseDataforChart();
	
	// 매출 표 일별, 월별, 년별 차트 표시
	@Select("SELECT DATE(purchase_regdate) as purchase_regdate, SUM(purchase_total) as purchase_total"
			+ " FROM purchase "
			+ " GROUP BY DATE(purchase_regdate)")
	List<PurchaseDto> getPurchaseDataforDay();
	
	@Select("SELECT DATE_FORMAT(purchase_regdate, '%Y-%m') as purchase_regdate, SUM(purchase_total) as purchase_total"
			+ " FROM purchase "
			+ " GROUP BY DATE_FORMAT(purchase_regdate, '%Y-%m')")
	List<PurchaseDto> getPurchaseDataforMonth();
	
	@Select("SELECT YEAR(purchase_regdate) as purchase_regdate, SUM(purchase_total) as purchase_total"
			+ " FROM purchase"
			+ " GROUP BY YEAR(purchase_regdate)")
	List<PurchaseDto> getPurchaseDataforYear();
	
	// 차트 서치 관련
	// 나라별, 종류별, 판매완료 처리된 내용들만 출력
	@Select({
		"<script>"
		+ " select pl.productl_regdate as purchase_regdate, sum(pl.product_cnt * p.product_price) as purchase_total"
		+ " from product p join purchase_list pl on p.product_no = pl.product_num join purchase pu on pl.purchase_num = "
		+ " pu.purchase_no"
		+ " <if test='product_cntr != null and !product_cntr.isEmpty()'>"
		+ "	AND p.product_cntr = #{product_cntr}"
		+ " </if>"
		+ " <if test='product_type != null and !product_type.isEmpty()'>"
		+ "	AND p.product_type = #{product_type}"
		+ " </if>"
		+ " group BY DATE_FORMAT(pl.productl_regdate,'YY-MM-dd')"
		+ " order by pl.productl_regdate"
		+ "</script>"
	})
		List<PurchaseDto> getPurchaseDataforChartSearch(ProductBean bean);
	// 총 판매량
	@Select("SELECT COUNT(*) FROM purchase")
	int sales();
	
	// 총 매출
	@Select("SELECT  SUM(purchase_total) as purchase_total"
			+ " FROM purchase  ")
	int revenue();
	
	// 경매 상품 등록
	@Insert("INSERT INTO auction(product_num,auction_open,auction_close,auction_sprice) "
			+ "VALUES(#{product_num},STR_TO_DATE(#{auction_open},'%Y-%m-%d %H:%i:%s'),STR_TO_DATE(#{auction_close},'%Y-%m-%d %H:%i:%s'),#{auction_sprice})")
	int insertAuctionData(AuctionBean bean);
	
	// 경매 상품  조회
	@Select("select * from auction ORDER BY auction_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<AuctionDto> getAuctionPageAll(Pagination pagination);
	
	// 경매 상태 변경
	@Update("UPDATE auction SET auction_state = case when auction_state = 'Y' then 'N' "
			+ "when auction_state = 'N' then 'Y' ELSE 'Y' END WHERE auction_no = #{auction_no};")
	int updateAuctionStateProgress(int auction_no);
	
	// 타임딜 상품 등록
	@Insert("INSERT INTO timedeal(product_no,timedeal_begin,timedeal_end,timedeal_discnt) "
			+ "VALUES(#{product_no},STR_TO_DATE(#{timedeal_begin},'%Y-%m-%d %H:%i:%s'),STR_TO_DATE(#{timedeal_end},'%Y-%m-%d %H:%i:%s'),#{timedeal_discnt})")
	int insertTimeDealData(TimeDealBean bean);
	
	// 타임딜 상품 조회
	@Select("select * from timedeal ORDER BY timedeal_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<TimedealDto> getTimedealPageAll(Pagination pagination);
	
	@Update("UPDATE timedeal SET  timedeal_begin = STR_TO_DATE(#{timedeal_begin},'%Y-%m-%d %H:%i:%s'), timedeal_end = STR_TO_DATE(#{timedeal_end},'%Y-%m-%d %H:%i:%s'), timedeal_discnt = #{timedeal_discnt} WHERE product_no = #{product_no}")
	int updateTimedeal(TimeDealBean bean);
	
	// 경매 상태 변경
	@Update("UPDATE timedeal SET timedeal_state = case when timedeal_state = 'Y' then 'N' "
				+ "when timedeal_state = 'N' then 'Y' ELSE 'Y' END WHERE product_no = #{product_no};")
	int updateTimeStateProgress(int product_no);
	
	// 
	
}
