package com.bitcamp.home.data;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.home.CommandService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class CommandDataWriteOk implements CommandService {

	@Override
	public String executeCommand(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//파일업로드, 데이터를 request
		//데이터를 업로드(파일 업로드) 하는 기능은 cos.jar파일의 cos\com\oreilly\servlet\MultipartRequest.class 에 있다. 객체만 생성하면 자동으로 업로드가 된다.
		// cos\com\oreilly\servlet\multipart\DefaultFileRenamePolicy.class 이건 올리면 이름이 중복되지 않게 리네임해주는 기능을 한다.
		//cos.jar에 있는 MultipartRequest클래스가 파일업로드, 데이터를 request한다.
		
		/*	MultipartRequest는 매개변수가 여러가지다.
			1. request객체
			2. 업로드 할 위치를 절대주소 형식으로
		 	주의할점 : 이클립스에서 만든 upload 폴더를 우클릭해서 나오는 주소는 실제로 실행하는 주소가 아니다.
		 	D:\workspaceWeb\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\webMVC \ upload 여기가 업로드할 실제 주소다. (이클립스에 생성해도 여기에 복사되어 실행된다.)
		 	위 주소의 tmp0은 톰캣 서버다. 만약 서버를 지우면 업로드한 파일들도 삭제될수있다. 서버가 늘어나면 tmp1, tmp2 식으로 늘어난다.
		 	그래서 삭제하면 안되는 파일들은 업로드 받은 후에 복사해서 이클립스의 실행하여 익스플로러에서 프로젝트의 업로드(만든거upload)폴더로 옮기면된다.
		 	
		 	이 주소를 다 칠수는 없기때문에  절대주소를 구하는 방법을 이용한다. 
		 	req.getServletContext().getRealPath("/upload"); <--이렇게쓰면 upload폴더의 절대주소를 찾는다. 
		 	
		 	파일은 그 절대주소의 위치로 업로드된다.
		 
		 	3. 업로드할 파일의 최대 크기 (byte단위로) 
		 	용량 단위 -> 1024 = 1kbyte ... 1024*1024 = 1mbyte ... 1024*1024*1024 = 1gbyte 
		 	
		 	4. 한글 인코딩 코드
		 	5. 같은 파일명이 존재할때 파일명 변경
		*/
		
		String path = req.getServletContext().getRealPath("/upload"); //절대주소 구하기
		System.out.println("업로드할 위치="+path);
		int maxSize = 1024*1024*1024;
		DefaultFileRenamePolicy pol = new DefaultFileRenamePolicy(); //파일명 바꿔주기 객체만들기
		
		MultipartRequest mr = new MultipartRequest(req, path, maxSize, "UTF-8", pol); //이게 실행되는 순간 업로드는 끝난다.
		///////////////////////////////////////////////
		
		//폼의 정보를 vo에 담기    전페이지에서 구해오는건 위의 MultipartRequest에 다 담겨있다. 그 객체를 이용해서 넣으면된다.
		// mr에서 title과 content를 구해오는 이유는 mr에서 UTF-8로 인코딩을 해놨기때문이다. req로 구해오기위해 따로 req를 인코딩하면 mr에서 인코딩을 또 할수가 없다.
		// 그러니까 파일 첨부할때는 mr로 전 페이지 데이터를 가져오는게 맞다.
		DataVO vo = new DataVO();
		vo.setTitle(mr.getParameter("title")); //req가 아니라 mr인걸 확인
		vo.setContent(mr.getParameter("content"));
		
		HttpSession session = req.getSession();
		vo.setUserid((String)session.getAttribute("logId"));
		
		vo.setIp(req.getRemoteAddr());
		

		//업로드파일명 		a, b 파일 두개를 넣은경우       /  a, null / null, b 하나는 넣고 하나는 안넣은경우 
		//파일명 두개를 저장할 수 있는 배열을 하나 만든다.
		int idx=0;
		String fileName[] = new String[2]; //파일명이 한개든 두개든 인덱스는 0, 1

		//똑같은 이름의 파일을 업로드 한 경우 파일명이 변경되어있을수도있다.
		//MultipartRequest객체에서 사용자가(client)가 선택한 원래 파일 목록을 구해야한다.
		Enumeration fileList = mr.getFileNames(); 		// Enumeration = 컬렉션이다.
		while(fileList.hasMoreElements()) { //객체가 있는지 확인.. 있으면 실행
			String oldFileName = (String)fileList.nextElement(); //변경되기 전 파일명 .. Object로 돌아온다. 형변환해주자
			String newFileName = mr.getFilesystemName(oldFileName); //새로변경된 파일명을 구해오는 메소드. 원래 파일명을 넣어주면 변경된 파일명을 구해서 리턴해준다.
			
			System.out.println(oldFileName +"-->"+newFileName);
			
			if(newFileName!=null) { //새로구한 파일명이 있으면
				fileName[idx++] = newFileName;  //파일네임 인덱스에 넣어라. 1개가 되든 2개가 되든
			}
		}
		
		vo.setFilename(fileName); //이렇게 넣으면 vo의 setFilename에 세팅한 filename1, 2에 자동으로 배열 0,1번 인덱스 내용이 들어가게 만든걸로 인해서 vo의 filename1, 2도 세팅된다.
		
		DataDAO dao = new DataDAO();
		int result = dao.dataInsert(vo);
		
		//여기서 레코드 추가에 실패하면. 유령파일이 생성된다. 파일 지우는 작업을 해야한다. 
		//레코드 추가는 실패했지만 추가 전에 위에서 이미 파일은 서버에 업로드 되었기 때문이다.
		
		//레코드 추가 실패시 이미 업로드 된 파일을 삭제한다.
		if(result<=0) {
			for(String delFile : fileName) {
				if(delFile!=null) {  //파일명이 0번째는 없고 1번째에 있을수있으니까.
					File file = new File(path, delFile); //경로는 path(절대주소 세팅한거)
					file.delete();
				}
			}
		}
		
		req.setAttribute("result", result);
		
		return "/data/dataWriteOk.jsp";
	}

}
