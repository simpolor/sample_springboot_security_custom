package com.simpolor.app.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.simpolor.app.domain.Member;
import com.simpolor.app.service.MemberService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	private String rolePrefix = "ROLE_";
	
	@Autowired 
	private MemberService memberService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logger.info("=========================================================");
		logger.info("[R] AuthenticationProvider.authenticate");
		logger.info("=========================================================");
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String encodedPassword = new BCryptPasswordEncoder().encode(password); 
		
		logger.info("-- username : {}", username);
		logger.info("-- password : {}", password);
		logger.info("-- encodedPassword : {}", encodedPassword);
		logger.info("-- authentication.getCredentials() : {}", authentication.getCredentials());
	
		Member member = memberService.getMember(username);
		
		if(member != null) {
			
			logger.info("-- member.getMember_id() : {}", member.getMember_id());
			logger.info("-- member.getMember_pw() : {}", member.getMember_pw());
			logger.info("-- member.getMember_name() : {}",member.getMember_name());
			logger.info("-- member.getMember_email() : {}", member.getMember_email());
			
			if(password.equals(member.getMember_pw())) {
				Collection<GrantedAuthority> grantedAuthorities = memberService.getMemberRole(member.getMember_id());;
				if(grantedAuthorities != null) {
					return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
				}else{
					throw new BadCredentialsException("권한이 존재하지 않습니다.");
				}
			}else {
				throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			}
		}else{
			throw new BadCredentialsException("존재하지 않는 사용자입니다.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
