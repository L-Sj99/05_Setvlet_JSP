package edu.kh.member.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.member.dao.MemberDao;
import edu.kh.member.dao.MemberDaoImpl;
import edu.kh.member.dto.Member;
public class MemberServiceImpl implements MemberService{
	private MemberDao dao = null;
	
	private String[] gradeArr = {"일반", "골드", "다이아"};
	public MemberServiceImpl() throws FileNotFoundException, ClassNotFoundException, IOException {
		dao = new MemberDaoImpl();
	}
	
	
	@Override
	public Member getMember(int index) {
		return dao.getMember(index);
	}
	@Override
	public boolean addMember(String name, String phone) throws IOException {
		List<Member> memberList = dao.getMemberList();  
		
		for(Member member : memberList) {
			if( phone.equals(member.getPhone()) ) {
				return false;
			}
		}
		Member member = new Member(name, phone, 0, Member.COMMON);
		boolean result = dao.addMember(member);
		return result;
	}

	@Override
	public List<Member> getMemberList() {
		return dao.getMemberList();
	}

	@Override
	public List<Member> selectName(String searchName) {
		List<Member> memberList = dao.getMemberList();
		List<Member> searchList = new ArrayList<Member>();
		for(Member member : memberList) {
			if(member.getName().equals(searchName)) {
				searchList.add(member);
			}
		}
		
		return searchList;
	}
	@Override
	public int updateAmount(int index, int acc) throws IOException {
		
		Member target = dao.getMember(index);
		int before = target.getAmount();
		target.setAmount(before + acc);  
		int grade = 0;
		
		int currentAmount = target.getAmount();
		if(currentAmount < 100000)  	 grade = Member.COMMON;
		else if(currentAmount < 1000000) grade = Member.GOLD;
		else 							 grade = Member.DIAMOND;
			
		
		int result = 4;
		if(target.getGrade() != grade) {
			target.setGrade(grade);
			result = grade;
		}
		dao.saveFile();
		return result;
	}
	@Override
	public boolean updateMember(int index, String phone) throws IOException {
		
		Member target = dao.getMember(index);
		target.setPhone(phone);
		
		dao.saveFile();
		
		return true; 
	}
	@Override
	public boolean deleteMember(int index) throws IOException {
		List<Member> memberList = dao.getMemberList();
		Member target = dao.getMember(index);
		boolean result = memberList.remove(target);
		dao.saveFile();
		return result;
	}
}