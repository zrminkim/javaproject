package shop.model.board;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.common.model.QnaDto;
import shop.controller.admin.Pagination;
import shop.controller.board.QnaBean;

@Mapper
public interface DataMapperInter {
	// 제품 전체 값 출력--------------------------------

	@Select("select count(*) from qna")
	int getQnaCount();

	// QnA 페이징처리 메소드
	@Select("select * from qna ORDER BY qna_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<QnaDto> getQnaPageAll(Pagination pagination);

	// 종류 및 나라 검색에 맞는 테이블의 내용들 불러오기--------------
	@Select("select * from qna where ${searchSubject} like concat('%',#{searchValue},'%') ORDER BY qna_no DESC LIMIT #{rowCount} OFFSET #{offset}")
	List<QnaDto> detailQnaSearch(Pagination pagination);

	@Select("select count(*) from qna where ${searchSubject} like concat('%',#{searchValue},'%')")
	int getQnaDatailCount(QnaBean bean);

	// qna, user 조인 테이블에서 qna번호로 데이터 얻기
	@Select("select qna_no, user_num, user_name, qna_subject, qna_content, qna_secret, qna_rep from qna  left outer join user on user_num=user_no WHERE qna_no = #{param1}")
	QnaDto getQnaDetail(int no);

	// board.html - 유저 이름 찾기
	
	@Select("select username from user where user_no=(select user_num from qna where qna_no=#{qna_no})")
	String getUserName(int qna_no);
	

	// 제품 등록, 수정, 삭제
	@Insert("insert into qna(user_num, qna_subject, qna_content, qna_secret)"
			+ " values(#{user_num},#{qna_subject},#{qna_content}, #{qna_secret})")
	int insertQna(QnaBean qnaBean);

	@Update("update qna set qna_secret = #{qna_secret}, qna_subject=#{qna_subject}, qna_content=#{qna_content}  where qna_no = #{qna_no} and user_num=#{user_num}")
	int updateQna(QnaBean qnaBean);

	@Delete("delete from qna  WHERE qna_no = #{qna_no}")
	int deleteQna(int no);

	// 조회수 증가
	@Update("update qna set read_cnt=read_cnt+1")
	int updateReadcnt();

	@Insert("insert into qna(userpasswd) values(#{userpasswd}) where qna_no=#{qna_no}")
	int insertPasswd(String userpasswd, int qna_no);

	
	@Select("select userpasswd from qna where qna_no=#{qna_no}") 
	String getPasswd(int no);
	

}
