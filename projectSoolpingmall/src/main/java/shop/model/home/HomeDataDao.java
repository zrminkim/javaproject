package shop.model.home;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.common.model.ProductDto;
import shop.common.model.TimedealSelectDto;
import shop.controller.home.HomeBean;
import shop.controller.category.Pagination;



@Repository
public class HomeDataDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HomeDataMappingInterface mappingInterface;
	

	/*
	 * // 전체 자료 읽기 public List<MemDto> getDataAll() { List<MemDto> list =
	 * mappingInterface.selectAll(); logger.info("datas : " + list.size()); return
	 * list; }
	 */
	
	// 추천(모든) 카테고리 자료 읽기
		public List<ProductDto> getDataRecommendCategory() {
			List<ProductDto> list = mappingInterface.selectRecommendCategory();
			logger.info("datas : " + list.size());
			return list;
		}
		
		// 추천(소주) 카테고리 자료 읽기
				public List<ProductDto> getDataRecommendCategory1() {
					List<ProductDto> list = mappingInterface.selectRecommendCategory1();
					logger.info("datas : " + list.size());
					return list;
				}
				
				// 추천(소주) 카테고리 자료 읽기
				public List<ProductDto> getDataRecommendCategory2() {
					List<ProductDto> list = mappingInterface.selectRecommendCategory2();
					logger.info("datas : " + list.size());
					return list;
				}
				// 추천(소주) 카테고리 자료 읽기
				public List<ProductDto> getDataRecommendCategory3() {
					List<ProductDto> list = mappingInterface.selectRecommendCategory3();
					logger.info("datas : " + list.size());
					return list;
				}
				
	// latest 카테고리 자료 읽기
	public List<ProductDto> getDataLatestCategory() {
		List<ProductDto> list = mappingInterface.selectLatestCategory();
		logger.info("datas : " + list.size());
		return list;
	}	
	// toplate 카테고리 자료 읽기
	public List<ProductDto> getDataLatestCategory1() {
		List<ProductDto> list = mappingInterface.selectLatestCategory1();
		logger.info("datas : " + list.size());
		return list;
	}	
	// review 카테고리 자료 읽기
	public List<ProductDto> getDataLatestCategory2() {
		List<ProductDto> list = mappingInterface.selectLatestCategory2();
		logger.info("datas : " + list.size());
		return list;
	}	
	
	// 타임딜 상품 종류별로 읽기
	public List<TimedealSelectDto> getTimedealProduct(String type){
		List<TimedealSelectDto> tList = mappingInterface.getTimeDealProduct(type);
		return tList;
	}
				
				
				
				
	
				
				
		
	// 소주 카테고리 자료 읽기
	public List<ProductDto> getDataSojuCategory(Pagination pagination) {
		List<ProductDto> list = mappingInterface.selectSojuCategory(pagination);
		logger.info("datas : " + list.size());
		return list;
	}
	
	// 맥주 카테고리 자료 읽기
		public List<ProductDto> getDataBeerCategory(Pagination pagination) {
			List<ProductDto> list = mappingInterface.selectBeerCategory(pagination);
			logger.info("datas : " + list.size());
			return list;
		}
		
		// 와인 카테고리 자료 읽기
		public List<ProductDto> getDataWineCategory(Pagination pagination) {
			List<ProductDto> list = mappingInterface.selectWineCategory(pagination);
			logger.info("datas : " + list.size());
			return list;
		}
		
		// 위스키 카테고리 자료 읽기
		public List<ProductDto> getDataWiskeyCategory(Pagination pagination) {
			List<ProductDto> list = mappingInterface.selectWiskeyCategory(pagination);
			logger.info("datas : " + list.size());
			return list;
		}
		// 보드카 카테고리 자료 읽기
			public List<ProductDto> getDataVodkaCategory(Pagination pagination) {
			List<ProductDto> list = mappingInterface.selectVodkaCategory(pagination);
			logger.info("datas : " + list.size());
			return list;
		}
			// 리큐르 카테고리 자료 읽기 여기부터새로
						public List<ProductDto> getDataLiqueurCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectLiqueurCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						// 브랜디 카테고리 자료 읽기
						public List<ProductDto> getDataBrandiCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectBrandiCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						// 중국술 카테고리 자료 읽기
						public List<ProductDto> getDataChinaCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectChinaCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						// 일본술 카테고리 자료 읽기
						public List<ProductDto> getDataJapanCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectJapanCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						// 데킬라 카테고리 자료 읽기
						public List<ProductDto> getDataTequilaCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectTequilaCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						// 샴페인 카테고리 자료 읽기
						public List<ProductDto> getDataChampagneCategory(Pagination pagination) {
						List<ProductDto> list = mappingInterface.selectChampagneCategory(pagination);
						logger.info("datas : " + list.size());
						return list;
					}
						
						
						
						public List<ProductDto> detailSearchProduct(shop.controller.home.Pagination pagination) {
							List<ProductDto> deatailDto = mappingInterface.detailSearch(pagination);
							return deatailDto;
						}
						
						public int getDetailCount(HomeBean bean) {
							int detailCnt = mappingInterface.getDatailCount(bean);
							return detailCnt;
						}
			
			
			
			
			
			
			
			
			
			
			
			
			
			public int getCount() {
				int count = mappingInterface.getCount();
				return count;
			}
			
			public List<ProductDto> getProductPageAll(Pagination pagination){
				List<ProductDto> list = mappingInterface.getProductPageAll(pagination);
				logger.info("datas :" + list.size());
				return list;
			}
			
	

	// 레코드 한개 읽기
	public ProductDto getData(int no) {
		ProductDto dto = mappingInterface.selectPart(no);
		return dto;
	}
	
	public List<ProductDto> searchData(String keyword) {
	    List<ProductDto> list = mappingInterface.searchData(keyword);
	    return list;
	}
	
	

}
