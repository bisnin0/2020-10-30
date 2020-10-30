package com.bitcamp.home.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandDownloadCount implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int no = Integer.parseInt(req.getParameter("no"));
		
		DataDAO dao = new DataDAO();
		//다운로드를 증가한후 현재 다운로드 횟수를 구하여 리턴받는다.
		int count = dao.downloadCount(no);
		
		req.setAttribute("count", count);
		
		return "/data/downcount.jsp";
	}

}
