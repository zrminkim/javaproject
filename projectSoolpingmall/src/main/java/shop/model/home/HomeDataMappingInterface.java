package shop.model.home;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import shop.common.model.ProductDto;
import shop.common.model.TimedealSelectDto;
import shop.controller.admin.ProductBean;
import shop.controller.category.Pagination;
import shop.controller.home.HomeBean;

@Mapper
public interface HomeDataMappingInterface {
	/*
	 * @Select("select * from product") List<MemDto> selectMain();
	 */
	
	//추천
	@Select("select * from product where product_type='sake'")
	List<ProductDto> selectRecommendCategory();
	//추천(소주)
	@Select("select * from product WHERE product_type='soju'")
	List<ProductDto> selectRecommendCategory1();
	//추천(소주)
	@Select("select * from product WHERE product_type='spirit_whiskey'")
	List<ProductDto> selectRecommendCategory2();
	//추천(소주)
	@Select("select * from product WHERE product_type='beer'")
	List<ProductDto> selectRecommendCategory3();	
	
	
	
	//latest
	@Select("select * from product WHERE product_type='latest'")
	List<ProductDto> selectLatestCategory();
	
	//toplate
	@Select("select * from product WHERE product_type='toplate'")
	List<ProductDto> selectLatestCategory1();
	
	//review
	@Select("select * from product WHERE product_type='review'")
	List<ProductDto> selectLatestCategory2();
	
	
	
		
	
	//소주
	@Select("select * from product WHERE product_type='soju' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}" )
	List<ProductDto> selectSojuCategory(Pagination pagination);
	//맥주
	@Select("select * from product WHERE product_type='beer' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectBeerCategory(Pagination pagination);
	//와인
	@Select("select * from product WHERE product_type='wine_red' OR product_type='wine_white' OR product_type='wine_rose' OR product_type='wine_spakling' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectWineCategory(Pagination pagination);
	//위스키
	@Select("select * from product WHERE product_type='spirit_whiskey' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectWiskeyCategory(Pagination pagination);
	//보드카
	@Select("select * from product WHERE product_type='spirit_vodka' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectVodkaCategory(Pagination pagination);
	//리큐르 여기부터해야함
	@Select("select * from product WHERE product_type='spirit_liqueur' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectLiqueurCategory(Pagination pagination);
	//브랜디
	@Select("select * from product WHERE product_type='spirit_brandy' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectBrandiCategory(Pagination pagination);
	//중국술
	@Select("select * from product WHERE product_type='spirit_gin' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectChinaCategory(Pagination pagination);
	//일본술
	@Select("select * from product WHERE product_type='sake' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectJapanCategory(Pagination pagination);
	//데킬라
	@Select("select * from product WHERE product_type='spirit_tequilla' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectTequilaCategory(Pagination pagination);
	
	//샴페인
	/*이 코드는 MyBatis의 어노테이션인 @Select를 사용하여 SQL 쿼리를 정의하는 코드입니다. 
	 * 해당 쿼리는 product 테이블에서 product_type이 "champagne"인 데이터를 product_no를 기준으로 내림차순으로 정렬한 뒤, rowCount 개수만큼 데이터를 가져옵니다. 
	 * 이때 offset은 가져올 데이터의 시작 위치를 나타냅니다. 예를 들어 rowCount이 10이고 offset이 20이라면, 21번째부터 30번째 데이터까지 가져옵니다. 
	 * 이를 페이징 기능에 사용할 수 있습니다.*/
	@Select("select * from product WHERE product_type='spirit_rum' ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> selectChampagneCategory(Pagination pagination);
	
	
	
	@Select("select * from product where product_no=#{product_no}")
	ProductDto selectPart(int no);
		
	@Select("SELECT * FROM product WHERE product_name LIKE CONCAT('%', #{keyword}, '%')")
	List<ProductDto> searchData(String keyword);
	
	@Select("select * from product ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> getProductPageAll(Pagination pagination);
	
	@Select("select count(*) from product")
	int getCount();
	
	@Select("select max(product_no) from product")
	int product_noCnt();
	// 종류 및 나라 검색에 맞는 테이블의 내용들 불러오기
	@Select("select * from product where product_name like concat('%',#{searchValue},'%') ORDER BY product_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<ProductDto> detailSearch(shop.controller.home.Pagination pagination);
	
	@Select("select count(*) from product where product_name like concat('%',#{searchValue},'%')")
	int getDatailCount(HomeBean bean);
		
	// 타임딜 상품 타입별 조회
	@Select("SELECT p.product_no, p.product_type, p.product_name , p.product_img, p.product_price, (p.product_price-(p.product_price * (t.timedeal_discnt * 0.01))) AS discount, timedeal_discnt"
			+ " FROM timedeal t, product p WHERE t.product_no = p.product_no AND product_type like concat('%',#{type},'%')  ")
	List<TimedealSelectDto> getTimeDealProduct(String type);
}

