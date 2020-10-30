package com.bitcamp.home.board;

import java.util.ArrayList;
import java.util.List;

import com.bitcamp.home.DBConnection;

public class BoardDAO extends DBConnection implements BoardInterface{
	public static BoardDAO getInstance() {
		return new BoardDAO();
	}
	//레코드 1개 선택
	@Override
	public void getSelect(BoardVO vo) { //글선택
		try {
			getConn();
			String sql = "select no, subject, content, userid, hit, writedate from freeboard where no="+vo.getNo();
			
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, vo.getNo()); 위에서 sql 쿼리문에 넣었음. 
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setUserid(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setWritedate(rs.getString(6));
			}
			//조회수올리는거는 다른곳에서 해보기로
			
			
		}catch(Exception e) {
			System.out.println("레코드 선택 에러.."+e.getMessage());
		}finally {
			getClose();
		}
	}

	@Override
	public int getAllRecordCount(PagingVO pageVO) {
		int totalRecord=0;
		try {
			getConn();
			String sql = "select count(no) from freeboard";
			if(pageVO.getSearchWord()!=null && !pageVO.getSearchWord().equals("")) {
				sql += " where " +pageVO.getSearchKey()+" like '%"+pageVO.getSearchWord()+"%'";
			}
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			rs.next();
			totalRecord = rs.getInt(1);
		}catch(Exception e) {
			System.out.println("총 레코드 수 구하기 에러-->"+e.getMessage());
		}finally {
			getClose();
		}
		
		return totalRecord;
	}

	//조회수 증가
	@Override
	public void hitCount(int no) {
		try {
			getConn();
			String sql ="update freeboard set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("조회수 증가 에러-->"+e.getMessage());
		}finally {
			getClose();
		}
	}

	@Override
	public int boardUpdate(BoardVO vo) {
		int cnt=0;
		try {
			getConn();
			String sql ="update freeboard set subject=?, content=? where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getSubject());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			
			cnt = pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("게시판 글수정 에러 발생-->"+e.getMessage());
		}finally {
			getClose();
		}
		
		
		return cnt;
	}

	@Override
	public int boardDelete(int no) {
		int cnt=0;
		try {
			getConn();
			String sql ="delete from freeboard where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			cnt = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("게시글 삭제 에러-->"+e.getMessage());
		}finally {
			getClose();
		}
		return cnt;
	}

	@Override
	public List<BoardVO> getListRecord(PagingVO pVO) { //검색기능 구현 + 게시판 페이지 출력 레코드(이해하기)
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			getConn();
			String sql ="select * from"
					+ "(select * from"
					+ "(select no, subject, userid, hit, to_char(writedate,'MM-DD HH:MI') writedate "
					+ " from freeboard ";
					//검색어가 있을때
					if(!(pVO.getSearchKey()==null || pVO.getSearchWord()==null)) { //부정으로 두개 다 있을때로 바꿈
						sql += " where "+pVO.getSearchKey()+" like ? "; //검색어가 포함된걸 찾는다. 검색어가 없으면 이게 실행 안된다.
					}
					sql += " order by no desc) " //여기까지가 한개의 서브쿼리인것 --> 모든 레코드를 출력
						+ " where rownum<=? order by no asc) " //이게 두번째 서브쿼리 --> 모든 레코드에서 rownum을 기준으로 뒤에걸 버린다(현재페이지 레코드)
						+ " where rownum<=? order by no desc"; //이 쿼리문을 실행하면 원하는 페이지가 나온다.(검색어 포함해서)

					pstmt = conn.prepareStatement(sql);
		//검색어가 있을때
		if(!(pVO.getSearchKey()==null || pVO.getSearchWord()==null)) {
			pstmt.setString(1, "%"+pVO.getSearchWord()+"%");
			pstmt.setInt(2, pVO.getNowPage()*pVO.getOnePageRecord()); //현재페이지*한페이지 총 레코드 = 3페이지면 30개 출력 
			pstmt.setInt(3, pVO.getLastPageRecordCount());  //그중에서 높은숫자(가장최근게시물)만큼 한페이지 총 레코드수만큼만 남기고 나머지 삭제시킴 그러면 그 페이지 게시물만 출력된다.
		}else { //검색어가 없을때
			pstmt.setInt(1, pVO.getNowPage()*pVO.getOnePageRecord());
			pstmt.setInt(2, pVO.getLastPageRecordCount());
		}
			
		rs = pstmt.executeQuery();
		while(rs.next()) { //null 왜넣는지 기억하기. BoardVO에서 생성자 만들때 null부분에 content를 넣었는데 여기선 그 값이 없으니까 그냥 null넣고 지나간것. 생성자에 또 만들기 귀찮으면 이렇게해도된다.
			list.add(new BoardVO(rs.getInt(1), rs.getString(2), null, rs.getString(3), rs.getInt(4), rs.getString(5))); //중간에 생성자에서 content 부분을 빼먹고 쓰려면 null을 쓰면 된다.
		}
		}catch(Exception e) {
			System.out.println("게시판 레코드 선택 에러 발생-->"+e.getMessage());
		}finally {
			getClose();
		}
		
		return list;
	}
	
	@Override
	public int boardInsert(BoardVO vo) {
		int cnt=0;
		try {
			getConn();
			String sql = "insert into freeboard(no, subject, content, userid, hit, writedate, ip) values(a_sq.nextval, ?, ?, ?, 0, sysdate, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getSubject());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserid());
			pstmt.setString(4, vo.getIp());
			
			cnt = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("게시글 등록 에러-->"+e.getMessage());
		}finally {
			getClose();
		}
		
		return cnt;
	}

}
