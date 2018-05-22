package com.simpolor.app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;

import com.simpolor.app.domain.User;

@Mapper
public interface UserRepository {

	public User readUser(String username);
	public List<GrantedAuthority> readAuthority(String username);
	public void createUser(User user); 
	public void createAuthority(User user); 
	public void deleteUser(String username); 
	public void deleteAuthority(String username);
	
}
