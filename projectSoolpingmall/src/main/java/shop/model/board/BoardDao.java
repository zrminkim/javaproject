package shop.model.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.common.model.QnaDto;
import shop.controller.admin.Pagination;
import shop.controller.board.QnaBean;

@Repository
public class BoardDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataMapperInter dataMapperInter;
	
	
	
	public int getQnaDetailCount(QnaBean bean) {
		int qnadetailCnt = dataMapperInter.getQnaDatailCount(bean);
		return qnadetailCnt;
	}
	
	public List<QnaDto> detailQnaSearchProduct(Pagination pagination) {
		List<QnaDto> deatailQnaDto = dataMapperInter.detailQnaSearch(pagination);
		return deatailQnaDto;
	}
	
	
	//게시판 관련
	public int getQnaCount() {
		int count = dataMapperInter.getQnaCount();
		return count;
	}
	// 게시판 페이징 처리 필요
	public List<QnaDto> getQnaAll(Pagination pagination){
		List<QnaDto> list = dataMapperInter.getQnaPageAll(pagination);
		logger.info("datas :" + list.size());
		return list;
	}
	
	public boolean insertQna(QnaBean qnaBean) {
		try {
			int re =  dataMapperInter.insertQna(qnaBean);
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("insert fail :" + e.getMessage());
			return false;
		}
	}
	
	// 작성자 얻기
	public QnaDto getQnaDetail(int qna_no){
		QnaDto dto = dataMapperInter.getQnaDetail(qna_no);
		return dto;
	}
	
	
	
	public boolean updateQna(QnaBean qnaBean) {
		try {
			int re =  dataMapperInter.updateQna(qnaBean);
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("update fail :" + e.getMessage());
			return false;
		}
	}

	
	public boolean updateReadcnt() {
		try {
			int re =  dataMapperInter.updateReadcnt();
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("updatecnt fail :" + e.getMessage());
			return false;		
		}
	}
	
	
	public boolean deleteQna(int qna_no) {
		try {
			int re =  dataMapperInter.deleteQna(qna_no);
			if(re >0) return true;
			else return false;
			
		} catch (Exception e) {
			logger.info("delete fail :" + e.getMessage());
			return false;	
		}
	}
	
	public boolean insertPasswd(String user_passwd, int qna_no) {
		try {
			int re =  dataMapperInter.insertPasswd(user_passwd, qna_no);
			if(re >0) return true;
			else return false;
		} catch (Exception e) {
			logger.info("insert fail :" + e.getMessage());
			return false;		
		}
		
	}
	
	
	public String getPasswd(int qna_no) { 
		String user_passwd = dataMapperInter.getPasswd(qna_no);
		return user_passwd;
	}
	
	
	
	public String getUserName(int qna_no) {
		String user_name = dataMapperInter.getUserName(qna_no);
		return user_name;
	}
	

	
}
