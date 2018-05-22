package com.simpolor.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.simpolor.app.domain.Member;
import com.simpolor.app.service.MemberService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = userService.readUser(username);
        user.setAuthorities(userService.getAuthorities(username));
       
        return user;
	}
	
	public PasswordEncoder passwordEncoder() { 
		return userService.passwordEncoder(); 
	}

}
