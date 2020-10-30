package com.bitcamp.home.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;

public class CommandRegOutOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//과제로 내가 한 회원탈퇴
		//과제로 내가 한 회원탈퇴
		RegisterVO vo = new RegisterVO();
		vo.setUserid((String)req.getParameter("userid"));
		vo.setUserpwd((String)req.getParameter("userpwd"));
		
		RegisterDAO dao = new RegisterDAO();
		int result = dao.passwordCheck(vo.getUserid(), vo.getUserpwd());
		
		
		if(result>0) {
			int result2 = dao.registerDelete(vo);//DAO작업 회원삭제
			if(result2>0) {
				HttpSession session = req.getSession();
				session.invalidate();
			}
			req.setAttribute("result2", result2);//삭제결과 req
			return "/register/regOutResult.jsp";
		}else {
			return "/register/pwdResult.jsp";
		}
	}

}
