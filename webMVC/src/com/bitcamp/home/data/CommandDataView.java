package com.bitcamp.home.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandDataView implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DataVO vo = new DataVO();
		vo.setNo(Integer.parseInt(req.getParameter("no")));
		
		DataDAO dao = new DataDAO();
		//조회수 증가 .. 이게 레코드선택보다 위로가야한다.
		dao.hitCount(vo.getNo());
		//레코드 선택
		dao.getSelect(vo); //같은 주소로 작업해야 하니까 vo를 넘겨줘야한다.

		
		req.setAttribute("vo", vo);
		
		
		return "/data/dataView.jsp";
	}

}
