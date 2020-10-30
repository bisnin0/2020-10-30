package com.bitcamp.home.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandEditFormOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		RegisterVO vo = new RegisterVO();
		vo.setUserid(req.getParameter("userid"));
		vo.setUserpwd(req.getParameter("userpwd"));
		vo.setUsername(req.getParameter("username"));
		vo.setGender(req.getParameter("gender"));
		vo.setYear(req.getParameter("year"));
		vo.setMonth(req.getParameter("month"));
		vo.setDay(req.getParameter("day"));
		vo.setTel1(req.getParameter("tel1"));
		vo.setTel2(req.getParameter("tel2"));
		vo.setTel3(req.getParameter("tel3"));
		vo.setEmail(req.getParameter("email"));
		vo.setZipcode(req.getParameter("zipcode"));
		vo.setAddr(req.getParameter("addr"));
		vo.setAddrDetail(req.getParameter("addrDetail"));
		
		
		RegisterDAO dao = RegisterDAO.getInstance();
		//비밀번호 일치여부 확인
		int pwdResult = dao.passwordCheck(vo.getUserid(), vo.getUserpwd());
		if(pwdResult==0) {//비밀번호 잘못입력
			return "/register/pwdResult.jsp";
		}else{ //비밀번호가 맞을경우 회원정보를 수정한다.
			int result = dao.registerUpdate(vo);
			req.setAttribute("result", result);
			return "/register/regEditResult.jsp";
			
		}
	}

}
