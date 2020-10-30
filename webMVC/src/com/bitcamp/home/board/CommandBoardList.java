package com.bitcamp.home.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;

public class CommandBoardList implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//총레코드 수
		BoardDAO dao = BoardDAO.getInstance();
		PagingVO pageVO = new PagingVO();
		
		
		//현재페이지
		String nowPageTxt = req.getParameter("nowPage");
		if(nowPageTxt!=null) { //페이지 번호를 request한경우(서버로 가져온경우) ..  request한게 없을경우 기본세팅인 1페이지가 찍힌다(VO에 1써놈)
			pageVO.setNowPage(Integer.parseInt(nowPageTxt)); //페이지 번호를 세팅 
		}


		//검색어, 검색키
		String sWord = req.getParameter("searchWord");
		if(!(sWord == null || sWord.equals(""))) {  //request했는데 검색어가 없을때를 부정!으로 바꿔서 검색어가 있을때로 조건변경
			pageVO.setSearchKey(req.getParameter("searchKey"));
			pageVO.setSearchWord(sWord);
		}		
		
		//총 레코드 수 구하기. (위치 중요. VO에 세팅한 마지막레코드수 구하는것에 nowPage가 필요한데 그걸 현재페이지 구하기에서 구하고있으니. 이게 그 위로가면 nowPage는 무조건 1로 계산되어나온다.)
		//총 레코드 수 구하기 .. 검색어, ㄱ
		pageVO.setTotalRecord(dao.getAllRecordCount(pageVO)); 
		
		
		//시작페이지 번호 계산 이걸 VO의 setNowPage에서 구현함. 위에서 setNowPage에 현재페이지번호 셋팅하면 자동으로 계산된다.
		//pageVO.setStartPageNum(((pageVO.getNowPage()-1)/pageVO.getOnePageNumCount())*pageVO.getOnePageNumCount()+1);
		

		List<BoardVO> list = dao.getListRecord(pageVO);
		
		
		
		req.setAttribute("pageVO", pageVO);
		req.setAttribute("list", list);
		
		return "/board/boardList.jsp";
	}

}
