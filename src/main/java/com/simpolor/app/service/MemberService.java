package com.simpolor.app.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simpolor.app.domain.Member;

public interface MemberService {
	
	public Collection<GrantedAuthority> getAuthorities(String username);
	public Member readUser(String username); 
	public void createUser(Member user); 
	public void deleteUser(String username); 
	public PasswordEncoder passwordEncoder();

}
