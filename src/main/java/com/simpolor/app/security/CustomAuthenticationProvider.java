package com.simpolor.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	private String rolePrefix = "ROLE_";
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logger.info("=========================================================");
		logger.info("[R] AuthenticationProvider.authenticate");
		logger.info("=========================================================");
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String passwordEnc = customUserDetailsService.passwordEncoder().encode(password);
		
		logger.info("-- username : {}", username);
		logger.info("-- password : {}", password);
		logger.info("-- passwordEnc : {}", passwordEnc);
	
		UserDetails userDetails;
		try {
			userDetails = customUserDetailsService.loadUserByUsername(username);
			
			logger.info("-- userDetails.getUsername() : {}", userDetails.getUsername());
			logger.info("-- userDetails.getPassword() : {}", userDetails.getPassword());
			logger.info("-- userDetails.getAuthorities() : {}", userDetails.getAuthorities());
			
			logger.info("-- password 비교1 : {}", customUserDetailsService.passwordEncoder().matches(passwordEnc, userDetails.getPassword()));
			logger.info("-- password 비교2 : {}", customUserDetailsService.passwordEncoder().matches(password, userDetails.getPassword()));
			
			if(customUserDetailsService.passwordEncoder().matches(passwordEnc, userDetails.getPassword())) {
				throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			}
			
			return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
			
		} catch(UsernameNotFoundException e) { 
			logger.info(e.toString()); 
			throw new UsernameNotFoundException(e.getMessage()); 
		} catch(BadCredentialsException e) { 
			logger.info(e.toString()); 
			throw new BadCredentialsException(e.getMessage()); 
		} catch(Exception e) { 
			logger.info(e.toString()); 
			throw new RuntimeException(e.getMessage()); 
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
