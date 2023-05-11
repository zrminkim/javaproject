package shop.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import groovy.util.logging.Slf4j;
import shop.common.model.QnaDto;
import shop.controller.admin.Pagination;
import shop.model.board.BoardDao;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	private BoardDao boardDao;

	// 게시판 출력
	@GetMapping("board") // 상품 상세 페이지로 변경##############3
	public String showboard(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		// 페이징 처리
		Pagination pagination = new Pagination(boardDao.getQnaCount(), page);
		List<QnaDto> list = (List<QnaDto>) boardDao.getQnaAll(pagination);

		// 작성자 이름 얻기 ###############
		model.addAttribute("qnas", list);
		model.addAttribute("page", page);
		model.addAttribute("pageVo", pagination);
		return "board/board"; // 상품 상세 페이지로 변경###########33
	}

	// 게시물 추가
	@GetMapping("boardinsert")
	public String getInsertQna() {
		return "board/boardinsert";
	}

	@PostMapping("boardinsert")
	public String postInsertQna(HttpServletRequest request, QnaBean qnaBean) {

		// 제목, 내용, 비밀글 설정여부, 비밀번호(수정용)를 입력받고

		// 세션에서 유저번호를 꺼내서 빈에 넣어 DB에 저장
		HttpSession session = request.getSession();
		qnaBean.setUser_num((Integer) session.getAttribute("user_no"));
		boolean b = boardDao.insertQna(qnaBean);
		if (b == true) {
			return "redirect:/board"; // 상품 상세페이지로 변경##########3
		} else {
			return "redirect:http://localhost/error";
		}
	}

	// 게시물 수정

	@GetMapping("boardupdate")
	public String getUpdateQna(@RequestParam("qna_no") int no, Model model) {
		// qna 번호로 수정할 객체를 찾음
		QnaDto dto = boardDao.getQnaDetail(no);
		// 수정할 객체를 수정 뷰로 넘김
		model.addAttribute("qna", dto);
		return "board/boardupdate";
	}

	@PostMapping("boardupdate")
	public String postUpdateQna(QnaBean qnaBean) {

		// 수정용 비밀번호 저장
		String db_pwd = boardDao.getPasswd(qnaBean.getQna_no()); // 수정한 글의 번호를 통해 비밀번호를 DB에서 조회
		String inp_pwd = qnaBean.getUserpasswd(); // 수정 시 입력받은 비밀번호
		// System.out.println("db_pwd : "+db_pwd);
		// System.out.println("inp_pwd : "+inp_pwd);

	
		// DB의 비밀번호와 입력받은 비밀번호가 일치하면 글을 수정
		if(db_pwd.equals(inp_pwd)) {
			boolean b = boardDao.updateQna(qnaBean);
			if (b) {
				return "redirect:/board";
			} else {
				return "redirect:http://localhost/error";
			}
		} else {
			return "redirect:http://localhost/error";
		}


	}

	// 게시물 제거
	@GetMapping("boarddelete")
	public String getDeleteQna(@RequestParam("qna_no") int no, Model model) {
		boolean b = boardDao.deleteQna(no);
		if (b) {
			return "redirect:/board"; // 상품 상세페이지로 변경
		} else {
			return "redirect:http://localhost/error";
		}
	}

}