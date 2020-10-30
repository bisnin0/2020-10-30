package com.bitcamp.home.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandBoardView implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		BoardVO vo = new BoardVO();
		PagingVO pVo = new PagingVO();
		
		vo.setNo(Integer.parseInt(req.getParameter("no")));
		pVo.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
		
		if(req.getParameter("searchWord")!=null) {
			pVo.setSearchKey(req.getParameter("searchKey"));
			pVo.setSearchWord(req.getParameter("searchWord"));
		}
		
		BoardDAO dao = new BoardDAO();
		
		//조회수 증가
		dao.hitCount(vo.getNo());
		//레코드 선택
		
		dao.getSelect(vo);
		
		req.setAttribute("vo", vo);
		req.setAttribute("pVo", pVo);
		
		return "/board/boardView.jsp";
	}

}
