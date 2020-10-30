package com.bitcamp.home.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandBoardDel implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//내가 푼거(정상작동함)
//		BoardVO vo = new BoardVO();
//		PagingVO pVo = new PagingVO();
//		vo.setNo(Integer.parseInt(req.getParameter("no")));
//		pVo.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
//		if(req.getParameter("searchWord")!=null) {
//			pVo.setSearchKey(req.getParameter("searchKey"));
//			pVo.setSearchWord(req.getParameter("searchWord"));
//		}
//		
//		BoardDAO dao = new BoardDAO();
//		int cnt = dao.boardDelete(vo.getNo());
//		
//		
//		req.setAttribute("no", vo.getNo());
//		req.setAttribute("pVo", pVo);
//		req.setAttribute("cnt", cnt);
//		
//		return "/board/boardDel.jsp";
		
		//정답(풀이 받아씀..)
		int no = Integer.parseInt(req.getParameter("no"));
		
		//페이지번호, 검색키, 검색어
		PagingVO pVo = new PagingVO();
		pVo.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
		String searchWordStr =req.getParameter("searchWord");
		if(searchWordStr !=null) {
			pVo.setSearchKey(req.getParameter("searchKey"));
			pVo.setSearchWord(searchWordStr);
		}
		
		BoardDAO dao = new BoardDAO();
		int cnt = dao.boardDelete(no);
		
		//req.setAttribute("no", no); 안보내도 된다.
		req.setAttribute("pVo",pVo);
		req.setAttribute("cnt", cnt);
		
		return "/board/boardDel.jsp";
				
				
		
	}

}
