package com.example.service;

import java.util.List;

import com.example.dao.MemberDAO;
import com.example.model.Member;

public class MemberServiceImpl implements MemberService {
	
	private MemberDAO memberDAO;
	public void setMemberDAO(MemberDAO memberDAOArg) {
		this.memberDAO = memberDAOArg;
	}

	@Override
	public void add(Member member) {
		// TODO Auto-generated method stub
		memberDAO.add(member);

	}

	@Override
	public List<Member> getMembers() {
		// TODO Auto-generated method stub
		return memberDAO.getMembers();
	}

}
