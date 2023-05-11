package shop.controller.detail;

import lombok.Data;

@Data
public class ReviewPagination {
	String product_no;
	int rowCount = 10; // 한 페이지 당 보여줄 게시물 개수
	int pageCount = 10; // 한 블럭에 몇 개의 페이지 개수
	int totalCount; // 총 게시물 개수
	int page; // 현재 페이지

	int startPage = 1; // 한 블럭의 시작 페이지: 기본 값 1 // ex) 1 2 3 4 5 일 때 1을 의미.
	int endPage; // 한 블럭의 끝 페이지

	int totalPageCount; // 총 페이지 개수

	boolean isPrev = false; // 다음 페이지로 이동하는 버튼 유무
	boolean isNext = false; // 이전 페이지로 이동하는 버튼 유무
	
	private String searchProduct,searchUser,searchSubject, searchValue;
	
	int offset; // 얼만큼 끊어서 가져올 것인가.

	public ReviewPagination(final int totalCount, final int page) {
	    
		// 총 페이지 개수 구하기
		setTotalPageCount(totalCount, this.rowCount); 

		// 한 블럭의 첫 페이지 구하기
		setStartPage(this.startPage, page, this.pageCount);

		// 한 블럭의 끝 페이지 구하기
		setEndpage(this.startPage, this.pageCount, this.totalPageCount); 

		// 이전 블록 버튼 유무 판별하기
		isPrev(page, this.pageCount); 

		// 다음 블록 버튼 유무 판별하기
		isNext(this.endPage, this.totalPageCount); 

		// offset 구하기
			setOffset(page, this.rowCount); 
	    }

	// 총 페이지 개수 구하기
	private void setTotalPageCount(final int totalCount, final int rowCount) {
		this.totalPageCount = (int) Math.ceil(totalCount * 1.0 / rowCount);
	}

	// 한 블럭의 첫 페이지 구하기
	private void setStartPage(final int startPage, final int page, final int pageCount) {
		this.startPage = startPage + (((page - startPage) / pageCount) * pageCount);
	}

	// 한 블럭의 끝 페이지 구하기
	private void setEndpage(final int startPage, final int pageCount, final int totalPageCount) {
		this.endPage = ((startPage - 1) + pageCount) < totalPageCount ? (startPage - 1) + pageCount : totalPageCount;
	}

	// 이전 블럭으로 이동할 버튼 생성 유무
	private void isPrev(final int page, final int pageCount) {
		this.isPrev = 1 < ((page * 1.0) / pageCount);
	}

	// 다음 블럭으로 이동할 버튼 생성 유무
	private void isNext(final int endPage, final int totalPageCount) {
		this.isNext = endPage < totalPageCount;
	}

	// offset 구하기 // 쿼리 select 시 끊어서 가져오기
	private void setOffset(final int page, final int rowCount) {
		this.offset = (page - 1) * rowCount;
	}
}
