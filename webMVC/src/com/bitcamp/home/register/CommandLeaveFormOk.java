package com.bitcamp.home.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;

public class CommandLeaveFormOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//1. 비밀번호 가져오기
		String userpwd = req.getParameter("userpwd");
		
		//2.세션에서 로그인한 아이디
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("logId");
		
		RegisterDAO dao = RegisterDAO.getInstance();
		int result = dao.regLeaveRecord(userid, userpwd);
		
		if(result>0) {//레코드가 삭제된경우
			session.invalidate(); //세션에 기록된 데이터 삭제(로그인아이디)
		}
		req.setAttribute("result", result);
		
		
		return "/register/regLeaveResult.jsp";
	}

}
