package com.bitcamp.home.data;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;

public class CommandDataDelete implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DataVO vo = new DataVO(); //레코드를 지우기전에 레코드의 파일명을 가져와야한다. 레코드를 지워버리면 파일명을 알 수 없으니까 미리 파일명 구해야한다.
		
		HttpSession session = req.getSession();
		vo.setUserid((String)session.getAttribute("logId"));
		
		vo.setNo(Integer.parseInt(req.getParameter("no")));
		
		DataDAO dao = new DataDAO();
		int cnt = dao.dataDelete(vo); //dataDelete(vo)에 파일명 세팅해주는 기능도 넣었음
		
		//레코드 삭제 확인 후 파일을 삭제한다.
		if(cnt>0) {
			String file[] = vo.getFilename();
			String path = req.getServletContext().getRealPath("/upload");
			
			for(String f:file) {
				if(f!=null) { //f가 null이 아닐때만 삭제.. f는 파일명
					File delFile = new File(path, f); //파일의 경로와 이름을 입력해서 파일을 객체로 만듬
					delFile.delete(); //그 파일 삭제
				}
			}
		}
		req.setAttribute("cnt", cnt);
		
		return "/data/dataDelOk.jsp";
	}

}
