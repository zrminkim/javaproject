package shop.model.detail;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetailDao {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DetailDataMappingInterface dataMappingInterface;
	
	//상품 클릭시 상세보기 페이지
	public DetailDto detailListAll(String product_no) {
		DetailDto dto = dataMappingInterface.DetailListAll(product_no);
		//logger.info("datas : " + dto);
		return dto;
	}
	
	// 찜하기 버튼 클릭
	public boolean wishlist(String product_no,String user_no) {
		boolean b = dataMappingInterface.wishlist(product_no,user_no); 
		//logger.info("datas : " + b);
		return b;
	}
	
	// 찜하기 데이터 DB 저장 
	public boolean wishIn(String product_no,String user_no) {
		boolean b = dataMappingInterface.wishIn(user_no,product_no); 
		//logger.info("datas : " + b);
		return b;
	}

	// 찜하기 데이터 DB 삭제 
	public boolean wishOut(String product_no,String user_no) {
		boolean b = dataMappingInterface.wishOut(user_no,product_no); 
		//logger.info("datas : " + b);
		return b;
	}
	
	// 조회수 증가
	public boolean updateViews(String product_no) {
		boolean b = dataMappingInterface.updateViews(product_no); 
		return b;
	}


}
