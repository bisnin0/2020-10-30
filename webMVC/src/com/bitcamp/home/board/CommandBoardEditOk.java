package com.bitcamp.home.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandBoardEditOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//
		PagingVO pVo = new PagingVO();
		pVo.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
		if(req.getParameter("searchWord")!=null) {
			pVo.setSearchKey(req.getParameter("searchKey"));
			pVo.setSearchWord(req.getParameter("searchWord"));
		}
		
		BoardVO vo = new BoardVO();
		vo.setNo(Integer.parseInt(req.getParameter("no")));
		vo.setSubject(req.getParameter("subject"));
		vo.setContent(req.getParameter("content"));
		
		BoardDAO dao = new BoardDAO();
		int cnt = dao.boardUpdate(vo);
		req.setAttribute("no", vo.getNo());
		req.setAttribute("pVo", pVo);
		req.setAttribute("cnt", cnt);
		return "/board/boardEditOk.jsp";
			
	}

}
