package com.bitcamp.home.data;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.home.CommandService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class CommandDataEditOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String path = req.getServletContext().getRealPath("/upload");
		int maxSize = 1024*1024*1024;
		DefaultFileRenamePolicy pol = new DefaultFileRenamePolicy();
		MultipartRequest mr = new MultipartRequest(req, path, maxSize, "UTF-8", pol);
		
		DataVO vo = new DataVO();
		vo.setNo(Integer.parseInt(mr.getParameter("no")));
		vo.setTitle(mr.getParameter("title"));
		vo.setContent(mr.getParameter("content"));
		
		//삭제한 파일명
		vo.setDelfile(mr.getParameterValues("delfile")); //배열에 넣는거라 데이터가 하나가 아니기때문에 getParameterValues로 넣는다.
		
		//새로 업로드한 파일명
		int idx=0;
		String fileName[] = new String[2]; //한개 아니면 두개  
		Enumeration fileList = mr.getFileNames();
		while(fileList.hasMoreElements()) {
			String old = (String)fileList.nextElement();
			String newFile = mr.getFilesystemName(old);
			if(newFile!=null) {
				fileName[idx++] = newFile;
			}
		}
		
		
		String[] del = vo.getDelfile();
		DataDAO dao = new DataDAO();
		
		System.out.println();
		if(idx<2) { //이전에 업로드한 파일은 다 지움
			String dbFile[] = dao.getFilename(vo.getNo());
			//데이터베이스에 있는 원래 파일명 얻어오기
			if(del != null) { //삭제파일이 있는 경우
				for(String dbFilename:dbFile) {
					int chk=0;
					for(String delFile:del) {
						if(dbFilename!=null && dbFilename.equals(delFile)) { //같으면 삭제하겠다는뜻
							chk++;
						}
					}
					
					if(chk==0) {
						fileName[idx++] = dbFilename;
					}
				}
			}else { //if del //삭제한 파일이없는경우
				for(String dbFilename:dbFile) {
					if(dbFilename!=null) {
						fileName[idx++] = dbFilename;
					}
				}
			}
		}
		
		vo.setFilename(fileName);
		int cnt = dao.dataUpdate(vo);
		
		//이전파일삭제
		if(cnt>0 && del != null) {
			for(String d: del) {
				File f = new File(path, d);
				f.delete();
			}
		}
		
		req.setAttribute("cnt", cnt);
		req.setAttribute("no", vo.getNo());
		
		return "/data/dataEditOk.jsp";
	}

}
