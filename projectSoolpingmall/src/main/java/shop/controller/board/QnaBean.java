package shop.controller.board;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class QnaBean {

	private int qna_no, user_num, qna_gno;
	private String qna_subject, qna_content, qna_reply,qna_secret,qna_rep,username,userpasswd; // 변수 이름 같으면 안돼
	private String qna_regdate, qna_moddate;
	private String searchSubject, searchValue;


	
}
