package com.simpolor.app.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simpolor.app.domain.User;
import com.simpolor.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Collection<GrantedAuthority> getAuthorities(String username) {
		Collection<GrantedAuthority> authorities = userRepository.readAuthority(username); 
		return authorities;
	}
	
	@Override 
	public User readUser(String username) { 
		User user = userRepository.readUser(username); 
		user.setAuthorities(userRepository.readAuthority(username)); 
		return user; 
	} 
	
	@Override 
	public void createUser(User user) { 
		String rawPassword = user.getPassword(); 
		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword); 
		user.setPassword(encodedPassword); 
		userRepository.createUser(user); 
		userRepository.createAuthority(user); 
	} 
	
	@Override 
	public void deleteUser(String username) { 
		userRepository.deleteUser(username); 
		userRepository.deleteAuthority(username); 
	} 
	
	@Override 
	public PasswordEncoder passwordEncoder() { 
		return this.passwordEncoder; 
	}

}
