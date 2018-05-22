package com.simpolor.app.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.simpolor.app.domain.Member;
import com.simpolor.app.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService userService;
	
	@RequestMapping("/member/login")
	public String userLogin() {
		return "userLogin";
	}
	
	@RequestMapping("/member/home")
	public ModelAndView userHome() {
		
		
		System.out.println("==================");
		System.out.println("memberHome");
		System.out.println("==================");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("memberHome");
		
		return mav;
	}
	
	/*@RequestMapping("/user/add")
	public ModelAndView userAdd() {
		
		Member userTemp = new Member(); 
		userTemp.setUsername("user1"); 
		userTemp.setPassword("pass1"); 
		userTemp.setAccountNonExpired(true); 
		userTemp.setAccountNonLocked(true); 
		userTemp.setName("USER1"); 
		userTemp.setCredentialsNonExpired(true); 
		userTemp.setEnabled(true); 
		userTemp.setAuthorities(AuthorityUtils.createAuthorityList("USER"));
		
		userService.deleteUser(userTemp.getUsername());
		
		userService.createUser(userTemp);
		
		Member user = userService.readUser(userTemp.getUsername());
		
		assertThat(user.getUsername(), is(userTemp.getUsername()));
		
		PasswordEncoder passwordEncoder = userService.passwordEncoder(); 
		
		assertThat(passwordEncoder.matches("pass1", user.getPassword()), is(true));
		
		Collection<? extends GrantedAuthority> grantedAuthorityList = user.getAuthorities();
		Iterator<? extends GrantedAuthority> it = grantedAuthorityList.iterator();
		
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) user.getAuthorities(); 
		
		while (it.hasNext()) { 
			GrantedAuthority authority = it.next(); 
			assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority()))); 
		} 
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userAdd");
		
		return mav;
	}*/
	
}
