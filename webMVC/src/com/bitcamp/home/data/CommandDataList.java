package com.bitcamp.home.data;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandDataList implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		DataDAO dao = new DataDAO();
		List<DataVO> list= dao.dataSelectAll();
		req.setAttribute("list", list);
		
		
		return "/data/dataList.jsp";
	}

}
