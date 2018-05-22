package com.simpolor.app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;

import com.simpolor.app.domain.Member;

@Mapper
public interface MemberRepository {

	public Member selectMember(String member_id);
	public List<GrantedAuthority> selectMemberRoleList(String member_id);
	public void createMember(Member member); 
	public void createMemberRole(Member member); 
	public void deleteMember(String member_id); 
	public void deleteMemberRole(String member_id);
	
}
