package com.bitcamp.home.data;

import java.util.ArrayList;
import java.util.List;

import com.bitcamp.home.DBConn;

public class DataDAO extends DBConn {
	
	//자료실 글등록
	public int dataInsert(DataVO vo) {
		int cnt=0;
		try {
			dbConn();
			sql = "insert into data(no, title, content, userid, filename1, filename2, ip) "
					+ " values(a_sq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserid());
			pstmt.setString(4, vo.getFilename1());
			pstmt.setString(5, vo.getFilename2());
			pstmt.setString(6, vo.getIp());
			cnt = pstmt.executeUpdate();
					
		}catch(Exception e) {
			System.out.println("자료실 레코드추가 에러발생-->"+e.getMessage());
		}finally {
			dbClose();
		}
		
		return cnt;
	}
	
	//자료실 목록 선택
	public List<DataVO> dataSelectAll(){
		List<DataVO> list = new ArrayList<DataVO>();
		try {
			dbConn();
			sql ="select no, title, userid, to_char(writedate, 'MM-DD HH:MI'), downcount, filename1, filename2 from data order by no desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
					
			while(rs.next()){
				DataVO vo = new DataVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setUserid(rs.getString(3));
				vo.setWritedate(rs.getString(4));
				vo.setDowncount(rs.getInt(5));
				vo.setFilename1(rs.getString(6));
				vo.setFilename2(rs.getString(7));
				list.add(vo);
			}
			
		}catch(Exception e) {
			System.out.println("자료실 목록 선택 에러-->"+e.getMessage());
		}finally {
			dbClose();
		}
		
		return list;
	}
	
	//
	public int downloadCount(int no) {
		int cnt=0;
		try {
			dbConn();
			
			//다운로드 횟수 증가
			sql = "update data set downcount=downcount+1 where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
			//현재 다운로드 횟수를 선택
			sql = "select downcount from data where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
			
		}catch(Exception e) {
			System.out.println("다운로드 횟수 증가 에러-->"+e.getMessage());
		}finally {
			dbClose();
		}
		
		return cnt;
	} 
	
	//레코드 선택
	public void getSelect(DataVO vo) { //리턴 없어도 여기서 받은 vo를 보내준곳과 같은주소의 vo를 쓰고 있기때문에 여기서 vo를 받아서 세팅해주면 보낸곳의 vo도 수정된다.
		try {
			dbConn();
			sql = "select no, userid, title, content, hit, downcount, "
					+ "writedate, filename1, filename2 from data where no=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getNo());
			
			rs= pstmt.executeQuery();
					
			if(rs.next()) {
				vo.setNo(rs.getInt(1));
				vo.setUserid(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setDowncount(rs.getInt(6));
				vo.setWritedate(rs.getString(7));
				vo.setFilename1(rs.getString(8));
				vo.setFilename2(rs.getString(9));
				
			}
		}catch(Exception e) {
			System.out.println("자료실 레코드 선택 에러-->"+e.getMessage());
		}finally {
			dbClose();
		}
	}
	
	
	public DataVO getSelect(int no) {
		// 레코드 선택시 이렇게 해도 되고 위에로 해도  되는데 이걸 쓰면 vo두번객체만들어야하니까 위에꺼로했음
		
		return null;
	}
	
	//조회수증가
	public void hitCount(int no) {
		try {
			dbConn();
			sql = "update data set hit=hit+1 where no="+no;
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.executeUpdate();
					
		}catch(Exception e) {
			System.out.println("조회수 증가 에러 발생-->"+e.getMessage());
		}finally {
			dbClose();
		}
	}
	
	//레코드 수정
	public int dataUpdate(DataVO vo) {
		int cnt=0;
		try {
			dbConn();
			sql = "update data set title=?, content=?, filename1=?, filename2=? where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getFilename1());
			pstmt.setString(4, vo.getFilename2());
			pstmt.setInt(5, vo.getNo());
			
			cnt = pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("자료실 레코드 수정 에러"+e.getMessage());
		}finally {
			dbClose();
		}
		return cnt;
	}
	
	//데이터베이스의 파일명 선택
	public String[] getFilename(int no) {
		String filename[] = new String[2];
		try {
			dbConn();
			sql = "select filename1, filename2 from data where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				filename[0] = rs.getString(1);
				filename[1] = rs.getString(2);
			}
			
		}catch(Exception e) {
			System.out.println("데이터베이스 파일명 선택 에러 ->"+e.getMessage());
		}finally {
			dbClose();
		}
		
		return filename;
	}
	
	public int dataDelete(DataVO vo) {
		int cnt=0;
		try {
			
			//삭제 전 파일명 보관
			vo.setFilename(getFilename(vo.getNo())); 
			//위의 getFilename 메소드 실행하고 그걸 vo의 Filename에 넣기. vo 의 setFilename에서 filename1과 2로 배열을 나눠넣는 기능 세팅해놨음
			
			dbConn();
			
			
			sql = "delete from data where no=? and userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getUserid());
			
			cnt = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("자료실 레코드 삭제 에러-->"+e.getMessage());
		}finally {
			dbClose();
		}
		return cnt;
	}
}
