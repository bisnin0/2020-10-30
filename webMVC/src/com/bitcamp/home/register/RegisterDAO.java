package com.bitcamp.home.register;

import java.util.ArrayList;
import java.util.List;

import com.bitcamp.home.DBConnection;

public class RegisterDAO extends DBConnection implements RegisterInterface {

	public static RegisterDAO getInstance() { //객체로만들기전에 리턴해줘야하니까 static
		return new RegisterDAO(); //현재클래스를 객체로 만들어서 리턴해주는(자신의 클래스를 리턴)
	}

	//회원가입
	@Override
	public int registerInsert(RegisterVO vo) {
		int cnt=0;
		try {
			getConn();          //생년월일은 2020-04-30식으로 들어오는데, 이건 문자다. 데이터셋팅은 DATE로 했으니 날짜로 바꿔줘야한다. 
			String sql = "insert into register(no, userid, userpwd, username, gender, birth, tel, email, zipcode, addr, addrdetail, regdate)"
					+ " values(a_sq.nextval, ?,?,?,?,to_date(?,'YYYY-MM-DD'),?,?,?,?,?,sysdate)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getUserpwd());
			pstmt.setString(3, vo.getUserpwd());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getBirth());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getZipcode());
			pstmt.setString(9, vo.getAddr());
			pstmt.setString(10, vo.getAddrDetail());
			
			
			cnt = pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			System.out.println("회원가입 DAO 에러"+e.getMessage());
		}finally {
			getClose();
		}
		
		return cnt;
	}

	@Override
	public void registerSelect(RegisterVO vo) {
		try {
			getConn();										//2020-10-10 문자형식으로 바꿈
			String sql = "select userid, username, gender, to_char(birth, 'YYYY-MM-DD') birth, tel, email, zipcode, addr, addrdetail "
					+ "from register where userid=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getUserid());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setUserid(rs.getString(1));
				vo.setUsername(rs.getString(2));
				vo.setGender(rs.getString(3));
				vo.setBirth(rs.getString(4));
				vo.setTel(rs.getString(5));
				vo.setEmail(rs.getString(6));
				vo.setZipcode(rs.getString(7));
				vo.setAddr(rs.getString(8));
				vo.setAddrDetail(rs.getString(9));
			}
			
		}catch(Exception e) {
			System.out.println("회원선택에러 발생--->"+e.getMessage());
		}finally {
			getClose();
		}
	}

	@Override
	public int idSearch(String userid) {
		int cnt = 0;
		try {
			getConn();
			String sql = "select count(userid) from register where userid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("아이디 중복검사 에러발생."+e.getMessage());
		}finally {
			getClose();
		}
		return cnt;
	}

	//우편번호 검색
	public List<ZipcodeVO> getZipcodeList(String doro) {
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		try {
			getConn();
			String sql = "select zipcode, sido, sigungu, um, doro, b_num1, b_num2, building, dong "
					+ " from zipcodetbl where doro like ?";
			//					 					'%백범로%'
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+doro+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ZipcodeVO vo = new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setSigungu(rs.getString(3));
				vo.setUm(rs.getString(4));
				vo.setDoro(rs.getString(5));
				vo.setB_num1(rs.getInt(6));
				vo.setB_num2(rs.getInt(7));
				vo.setBuilding(rs.getString(8));
				vo.setDong(rs.getString(9));
				list.add(vo);
			}
		}catch(Exception e) {
			System.out.println("우편번호 검색 에러..(Ajax)..."+e.getMessage());
		}finally {
			getClose();
		}
		return list;
	}

	@Override
	public void loginCheck(RegisterVO vo) {
		try {
			getConn();
			String sql = "select username from register where userid=? and userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getUserpwd());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //했더니 선택된게 있으면
				vo.setUsername(rs.getString(1));
				vo.setLogStatus("Y");
			}
			
			
		}catch(Exception e) {
			System.out.println("로그인 에러 발생"+e.getMessage());
		}finally {
			getClose();
		}
	}
	
	//회원정보 수정시 비밀번호 확인하는 메소드
	public int passwordCheck(String userid, String userpwd) {
		int result=0;
		try {
			getConn();
			String sql = "select count(userid) from register where userid=? and userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1); //0아니면 1
			}
		}catch(Exception e) {
			System.out.println("비밀번호 확인 에러.."+e.getMessage());
		}finally {
			getClose();
		}
		return result;
	}
	
	//회원정보 수정
	public int registerUpdate(RegisterVO vo) {
		int result=0;
		try {
			getConn();
			String sql = "update register set gender=?, birth=to_date(?,'YYYY-MM-DD'), tel=?, email=?, "
					+ "zipcode=?, addr=?, addrdetail=? where userid=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getGender());
			pstmt.setString(2, vo.getBirth());
			pstmt.setString(3, vo.getTel());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getZipcode());
			pstmt.setString(6, vo.getAddr());
			pstmt.setString(7, vo.getAddrDetail());
			pstmt.setString(8, vo.getUserid());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("회원정보 수정 실패.."+e.getMessage());
		}finally {
			getClose();
		}
		
		return result;
	}
	
	//내가 과제로 한 회원탈퇴
	public int registerDelete(RegisterVO vo) {
		int result=0;
		try {
			getConn();
			String sql = "delete from register where userid=? and userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getUserpwd());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("회원탈퇴 실패.."+e.getMessage());
		}finally {
			getClose();
		}
		
		
		return result;
	}
	
	//회원탈퇴 풀이 .. cascade를 테이블 생성 단계에서 적용해야 자식테이블의 데이터도 지워진다.. 게시판에 글쓴아이디는 삭제가 안된다.
	/*		테이블 제약조건(외래키) 넣을때 추가
	 * 	foreign key 세팅할때 넣어줘야함.(자식테이블에?)
		-- on delete restricted : 기본값
		-- on delete set null : 부모데이터가 삭제된 자식데이터를 null로 변경(사용하려면 not null이 아니어야한다. 수정하려면 퀄럼명(필드)를 리네임 하라고하는데? 왜 그래야하는지 잘..)
		-- on delete cascade : 부모데이터가 삭제되면 자식데이터도 삭제
	*/
	public int regLeaveRecord(String userid, String userpwd) {
		int cnt=0;
		try {
			getConn();
			String sql = "delete from register where userid=? and userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpwd);
			
			cnt = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("회원탈퇴 에러 발생-->"+e.getMessage());
		}finally {
			getClose();
		}
		return cnt;
	}
	
}
