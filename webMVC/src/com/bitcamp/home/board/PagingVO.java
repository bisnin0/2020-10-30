package com.bitcamp.home.board;

public class PagingVO {
	private int nowPage=1; //현재페이지
	private int totalRecord; //총레코드수
	private int totalPage; //총페이지수
	private int onePageRecord=10;//한페이지에 표시할 레코드 수
	private int onePageNumCount=10; //한번에 표시할 페이지 번호 숫자
	private int startPageNum=1; //페이지 번호의 시작페이지
	private int lastPageRecordCount=10; //마지막 페이지의 선택레코드 수
	
	//검색어와 관련된 변수
	private String searchKey;
	private String searchWord;
	
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		
		//시작페이지 번호 계산
		startPageNum=(nowPage-1)/onePageNumCount*onePageNumCount+1;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		//총 페이지수 구하기
		totalPage=(int)Math.ceil((double)totalRecord/onePageRecord); //나누고 올림한 값이 총 페이지수
		//마지막페이지에서 선택할 레코드수
		
		if(nowPage==totalPage && totalRecord%onePageRecord!=0) { 
			lastPageRecordCount = totalRecord%onePageRecord;
		}else {
			lastPageRecordCount = onePageRecord;
		}
		
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getOnePageRecord() {
		return onePageRecord;
	}
	public void setOnePageRecord(int onePageRecord) {
		this.onePageRecord = onePageRecord;
	}
	public int getOnePageNumCount() {
		return onePageNumCount;
	}
	public void setOnePageNumCount(int onePageNumCount) {
		this.onePageNumCount = onePageNumCount;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public int getLastPageRecordCount() {
		return lastPageRecordCount;
	}
	public void setLastPageRecordCount(int lastPageRecordCount) {
		this.lastPageRecordCount = lastPageRecordCount;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	
	
}
