package com.bitcamp.home.data;

public class DataVO {
	private int no;
	private String title;
	private String content;
	private String userid;
	private String filename1;
	private String filename2;
	private String filename[] = new String[2]; //파일명 담을거
	
	//삭제한 파일명
	private String delfile[];
	
	private String writedate;
	private int hit;
	private int downcount;
	private String ip;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}
	
	public String[] getFilename() {
		filename[0] = filename1;
		filename[1] = filename2;
		return filename;
	}
	public void setFilename(String[] filename) {
		this.filename = filename;
		
		this.filename1 = filename[0];
		this.filename2 = filename[1];
		//배열을 세팅하면 자동으로 1번 2번에 들어가게끔
	}
	
	public String[] getDelfile() {
		return delfile;
	}
	public void setDelfile(String[] delfile) {
		this.delfile = delfile;
	}
	public String getWritedate() {
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getDowncount() {
		return downcount;
	}
	public void setDowncount(int downcount) {
		this.downcount = downcount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
