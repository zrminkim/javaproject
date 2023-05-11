package shop.model.admin;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.common.model.AuctionDto;
import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.QnaDto;
import shop.common.model.TimedealDto;
import shop.common.model.UserDto;
import shop.controller.admin.AuctionBean;
import shop.controller.admin.AuctionController;
import shop.controller.admin.Pagination;
import shop.controller.admin.ProductBean;
import shop.controller.admin.PurchaseBean;
import shop.controller.admin.QnaBean;
import shop.controller.admin.TimeDealBean;
import shop.controller.admin.UserBean;


@Repository
public class DataDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataMappingInterface dataMappingInterface;
	
	//관리자 계정 리턴
	public AdminDto getAdminData(String id, String pwd){
		AdminDto dto = dataMappingInterface.getAdminData(id, pwd);
		return dto;
	}
	
	public List<String> getProduct_cntr(){
		List<String> cntrList = dataMappingInterface.showProduct_cntr();
		return cntrList;
	}
	public List<String> getProduct_type(){
		List<String> typeList = dataMappingInterface.showProduct_type();
		return typeList;
	}
	
	// 상품관련 Mybatis Mapping 메소드
	public int getCount() {
		int count = dataMappingInterface.getCount();
		return count;
	}
	public int product_noCnt() {
		int product_nocount = dataMappingInterface.product_noCnt();
		return product_nocount;
	}
	public int getDetailCount(ProductBean bean) {
		int detailCnt = dataMappingInterface.getDatailCount(bean);
		return detailCnt;
	}
	
	public int getUserDetailCount(UserBean bean) {
		int userdetailCnt = dataMappingInterface.getUserDatailCount(bean);
		return userdetailCnt;
	}
	public int getQnaDetailCount(QnaBean bean) {
		int qnadetailCnt = dataMappingInterface.getQnaDatailCount(bean);
		return qnadetailCnt;
	}
	public int getPurchaseDetailCount(PurchaseBean bean) {
		int purdetailCnt = dataMappingInterface.getPruchaseDatailCount(bean);
		return purdetailCnt;
	}
	
	
	public List<ProductDto> getProductAll(){
		List<ProductDto> list = dataMappingInterface.getProductAll();
		logger.info("datas :" + list.size());
		return list;
	}
	
	public List<ProductDto> getProductPageAll(Pagination pagination){
		List<ProductDto> list = dataMappingInterface.getProductPageAll(pagination);
		logger.info("datas :" + list.size());
		return list;
	}
	
	public ProductDto searchProduct(int no) {
		ProductDto dto = dataMappingInterface.searchProduct(no);
		return dto;
	}
	
	// 경매 상품 서치해서 해당 번호에 대한 정보 반환
	public AuctionDto searchAuction(int no) {
		AuctionDto dto = dataMappingInterface.searchAuction(no);
		return dto;
	}
	
	public List<ProductDto> detailSearchProduct(Pagination pagination) {
		List<ProductDto> deatailDto = dataMappingInterface.detailSearch(pagination);
		return deatailDto;
	}
	
	public List<UserDto> detailUserSearchProduct(Pagination pagination) {
		List<UserDto> deatailUserDto = dataMappingInterface.detailUserSearch(pagination);
		return deatailUserDto;
	}
	public List<QnaDto> detailQnaSearchProduct(Pagination pagination) {
		List<QnaDto> deatailQnaDto = dataMappingInterface.detailQnaSearch(pagination);
		return deatailQnaDto;
	}
	public List<PurchaseDto> detailPurchaseSearchProduct(Pagination pagination) {
		List<PurchaseDto> deatailQnaDto = dataMappingInterface.detailPurchaseSearch(pagination);
		return deatailQnaDto;
	}
	// 상품 추가
	public boolean insertProductData(ProductBean bean) {
			
			try {
				int re = dataMappingInterface.inserProduct(bean);
				if(re > 0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("insert fail :" + e.getMessage());
				return false;
			}
	}
	// 자료 수정
	public boolean updateProductData(ProductBean bean) {
		
		try {
			int re =  dataMappingInterface.updateProduct(bean);
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("update fail :" + e.getMessage());
			return false;
		}
	}
	public boolean deleteProductData(int no) {
		// 번호 중복 및 자동 증가 작업이 있어야함.
		try {
			int re =  dataMappingInterface.deleteProduct(no);
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("update fail :" + e.getMessage());
			return false;
		}
	}
	
	
	/////////////////////////////////////////////////////////////
	// 고객 관련 Mybatis Mapping 메소드
	//고객 수 반환
	public int getUserCount() {
		int count = dataMappingInterface.getUserCount();
		return count;
	}
	// 고객 페이징 처리 필요
	public List<UserDto> getUserAll(Pagination pagination){
		List<UserDto> list = dataMappingInterface.getUserPageAll(pagination);
		logger.info("datas :" + list.size());
		return list;
	}
	// 고객 상태 수정
		public boolean updateUser(int user_no) {
			try {
				int re =  dataMappingInterface.updateUserState(user_no);
				if(re >0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("update fail :" + e.getMessage());
				return false;
			}
		}
	
	//게시판 관련
		public int getQnaCount() {
			int count = dataMappingInterface.getQnaCount();
			return count;
		}
		// 게시판 페이징 처리 필요
		public List<QnaDto> getQnaAll(Pagination pagination){
			List<QnaDto> list = dataMappingInterface.getQnaPageAll(pagination);
			logger.info("datas :" + list.size());
			return list;
		}
		public boolean updateQnasecret(int qna_no) {
			try {
				int re =  dataMappingInterface.updateQnasecret(qna_no);
				if(re >0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("update fail :" + e.getMessage());
				return false;
			}
		}
		public boolean updateQnaApply(int qna_no) {
			try {
				int re =  dataMappingInterface.updateQnaReply(qna_no);
				if(re >0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("update fail :" + e.getMessage());
				return false;
			}
		}
		
		//주문관리 관련
				public int getPurchaseCount() {
					int count = dataMappingInterface.getPurchaseCount();
					return count;
				}
				// 게시판 페이징 처리 필요
				public List<PurchaseDto> getPurchaseAll(Pagination pagination){
					List<PurchaseDto> list = dataMappingInterface.getPruchasePageAll(pagination);
					logger.info("datas :" + list.size());
					return list;
				}
				public boolean updatePurchaseProgress(int purchase_no) {
					try {
						int re =  dataMappingInterface.updatePurchaseProgress(purchase_no);
						if(re >0) return true;
						else return false;
					} catch (Exception e) {
						logger.info("update fail :" + e.getMessage());
						return false;
					}
				}
		// 차트 관련
		public List<PurchaseDto> getPurchaseDataforCharts(){
			List<PurchaseDto> chartDate = dataMappingInterface.getPurchaseDataforChart();
			return chartDate;
		}
		public List<PurchaseDto> getPurchaseDataforChartsSearch(ProductBean bean){
			List<PurchaseDto> chartDateSearch = dataMappingInterface.getPurchaseDataforChartSearch(bean);
			return chartDateSearch;
		}
		// 일별, 월별, 년별 매출 반환 메소드
		public List<PurchaseDto> getPurchaseDataforDay(){
			List<PurchaseDto> salesDay = dataMappingInterface.getPurchaseDataforDay();
			return salesDay;
		}
		
		public List<PurchaseDto> getPurchaseDataforMonth(){
			List<PurchaseDto> salesMonth = dataMappingInterface.getPurchaseDataforMonth();
			return salesMonth;
		}
		public List<PurchaseDto> getPurchaseDataforYear(){
			List<PurchaseDto> salesYear = dataMappingInterface.getPurchaseDataforYear();
			return salesYear;
		}
		
		// 총 판매량
		public int totalSales() {
			int sales = dataMappingInterface.sales();
			return sales;
		}
		// 총 수익
		public int totalRevenue() {
			int revenue = dataMappingInterface.revenue();
			return revenue;
		}
		
		// 경매 상품 등록
		public boolean insertAuctionData(AuctionBean bean) {
			try {
				int re = dataMappingInterface.insertAuctionData(bean);
				if(re > 0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("insert fail :" + e.getMessage());
				return false;
			}
		}
		// 경매상품 조회
		public List<AuctionDto> getAuctionPageAll(Pagination pagination){
			List<AuctionDto> list = dataMappingInterface.getAuctionPageAll(pagination);
			logger.info("datas :" + list.size());
			return list;
		}
		
		public int getAuctionCount() {
			int count = dataMappingInterface.getAuctionCount();
			return count;
		}
		
	
		// 경매 상품 업데이트
		public boolean updateAuctiontData(AuctionBean bean) {
			try {
				int re =  dataMappingInterface.updateAuction(bean);
				if(re >0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("update fail :" + e.getMessage());
				return false;
			}
		}
		// 경매 상태 업데이트
		public boolean updateAuctionStateProgress(int auction_no) {
			try {
				int re =  dataMappingInterface.updateAuctionStateProgress(auction_no);
				if(re >0) return true;
				else return false;
			} catch (Exception e) {
				logger.info("update fail :" + e.getMessage());
				return false;
			}
		}
		
		// 타임딜 상품 등록
		public boolean insertTimedealData(TimeDealBean bean) {
					try {
						int re = dataMappingInterface.insertTimeDealData(bean);
						if(re > 0) return true;
						else return false;
					} catch (Exception e) {
						logger.info("insert fail :" + e.getMessage());
						return false;
					}
				}
		// 타임딜 상품 조회
		public List<TimedealDto> getTimedealPageAll(Pagination pagination){
			List<TimedealDto> list = dataMappingInterface.getTimedealPageAll(pagination);
			logger.info("datas :" + list.size());
			return list;
		}
		public int getTimeDealCount() {
			int count = dataMappingInterface.getTimedealCount();
			return count;
		}
		
		// 타임딜 상품 서치해서 해당 번호에 대한 정보 반환
		public TimedealDto searchTimedeal(int no) {
			TimedealDto dto = dataMappingInterface.searchTimedeal(no);
			return dto;
		}	
		
		// 타임딜 상품 업데이트
		public boolean updateTimedealData(TimeDealBean bean) {
					try {
						int re =  dataMappingInterface.updateTimedeal(bean);
						if(re >0) return true;
						else return false;
					} catch (Exception e) {
						logger.info("update fail :" + e.getMessage());
						return false;
					}
			}
				// 경매 상태 업데이트
		public boolean updateTimedealStateProgress(int product_no) {
					try {
						int re =  dataMappingInterface.updateTimeStateProgress(product_no);
						if(re >0) return true;
						else return false;
					} catch (Exception e) {
						logger.info("update fail :" + e.getMessage());
						return false;
					}
				}
}
