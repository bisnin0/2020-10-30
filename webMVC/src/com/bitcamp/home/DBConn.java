package com.bitcamp.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConn {
	/*
	 DBCP 설정후 db연결 설정하기.
	 연결 되어있는 상태에서 비어있는 회선을 가져오는 역할..
	 //============================DBCP 설정하고 테스트해보기.===================================
	 
	 
	 protected로 설정되어있는 변수는 상속안받으면 못쓴다.
	*/
	protected Connection con = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected String sql = null;
	
	public DBConn() {}
	
	//DB연결(연결은 이미 되어있는데 회선중에서 비이어있는 회선을 가져오는 메소드.. DB회선을 받아온다.)
	public void dbConn() {
		try {
			Context ctx = new InitialContext();
			//javax.naming거로 임포트
			Context envCtx = (Context)ctx.lookup("java:comp/env");
			
			DataSource ds = (DataSource)envCtx.lookup("jdbc/myoracle"); //커넥션을 구해온다. 연결되어있는 회선들을 구해옴
			
			con = ds.getConnection(); //비어있는 회선 하나를 꺼내서 con에 담아준다.
			
			
		}catch(Exception e) {
			System.out.println("DB연결 에러 발생----->");
			e.printStackTrace();
		}
	
	}
	
	//DB닫기(연결을 반납하는것) //이부분에 문제가 생기면 정상적으로 수행되다가 닫히지 않고 반복되어 중간에 멈추거나 하는 문제가 생긴다.
	public void dbClose() {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
			
		}catch(Exception e) {
			System.out.println("DB닫기 에러발생 ----->");
			e.printStackTrace();
		}
		
	}
}
