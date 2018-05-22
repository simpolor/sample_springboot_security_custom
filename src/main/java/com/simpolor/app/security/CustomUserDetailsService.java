package com.simpolor.app.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.simpolor.app.domain.Member;
import com.simpolor.app.service.MemberService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {
		
		logger.info("=========================================================");
		logger.info("[R] CustomUserDetailsService.loadUserByUsername");
		logger.info("=========================================================");
		
		logger.info("-- member_id : {}", member_id);
		
		Member member = memberService.getMember(member_id);
		if(member != null) {
			Collection<GrantedAuthority> grantedAuthorities = memberService.getMemberRole(member_id);
			
			// Spring security의 User의 값 형태
			String username = member.getMember_id();
			String password = passwordEncoder().encode(member.getMember_pw());
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			System.out.println("-- username : "+username);
			System.out.println("-- password : "+password);
			
			User user = new User(username, password, enabled, 
					accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
			
			return user;
		}else {
			throw new BadCredentialsException("존재하지 않는 사용자입니다.");
		}
	}

	public PasswordEncoder passwordEncoder() {
		return memberService.passwordEncoder();
	}

}
