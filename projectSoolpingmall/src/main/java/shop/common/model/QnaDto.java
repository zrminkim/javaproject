package shop.common.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class QnaDto {

	private int qna_no, user_num, qna_gno;
	private String qna_subject, qna_content, qna_reply,qna_secret,qna_rep,qna_type,user_name;
	private LocalDate qna_regdate, qna_moddate;
}
