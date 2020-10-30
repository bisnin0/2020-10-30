package com.bitcamp.home.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;

public class CommandEditForm implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//회원정보
		// session 로그인한 id를 구해서 db에서 회원정보를 선택한다.
		HttpSession session = req.getSession();
		
		RegisterVO vo = new RegisterVO();
		vo.setUserid((String)session.getAttribute("logId"));
		
		RegisterDAO dao = RegisterDAO.getInstance(); //이런식으로 객체를 만들어도 된다. (DAO에 자기자신의 클래스를 객체로 리턴해주는 메소드만듬)
		dao.registerSelect(vo);
		
		/////////////////////////////
		
		
		req.setAttribute("regVO", vo);
		
		System.out.println(vo.getUserid());
		System.out.println(vo.getUsername());
		System.out.println(vo.getGender());
		System.out.println(vo.getBirth());
		System.out.println(vo.getDay());
		
		
		
		return "/register/regEditform.jsp";
	}

}
