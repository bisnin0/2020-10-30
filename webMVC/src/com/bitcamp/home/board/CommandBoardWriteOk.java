package com.bitcamp.home.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;

public class CommandBoardWriteOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//내가 푼거(정상작동함)
//		req.setCharacterEncoding("UTF-8");
//		HttpSession session = req.getSession();
//		String userid2 = (String)session.getAttribute("logId");
//		String subject = req.getParameter("subject");
//		String content = req.getParameter("content");
//		String ipAddr = req.getRemoteAddr();
//		
//		
//		BoardVO vo = new BoardVO(subject, content, userid2, ipAddr);
//		BoardDAO dao = new BoardDAO();
//		int cnt = dao.boardInsert(vo);
//		req.setAttribute("cnt", cnt);
//		return "/board/boardWriteOk.jsp";
		
		//풀이 (차이 없음)
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		BoardVO vo = new BoardVO(req.getParameter("subject"), req.getParameter("content"), (String)session.getAttribute("logId"),req.getRemoteAddr());
		
		BoardDAO dao = new BoardDAO();
		int result = dao.boardInsert(vo);
		req.setAttribute("cnt", result);
		
		return "/board/boardWriteOk.jsp";
		
	}

}
