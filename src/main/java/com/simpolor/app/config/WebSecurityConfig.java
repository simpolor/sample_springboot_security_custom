package com.simpolor.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.simpolor.app.security.CustomAccessDeniedHandler;
import com.simpolor.app.security.CustomAuthenticationFailureHandler;
import com.simpolor.app.security.CustomAuthenticationProvider;
import com.simpolor.app.security.CustomAuthenticationSuccessHandler;
import com.simpolor.app.security.CustomLogoutSuccessHandler;
import com.simpolor.app.security.CustomSecurityInterceptor;
import com.simpolor.app.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired // 로그인에 대한 처리
	private CustomAuthenticationProvider customAuthenticationProvider; 
	
	@Autowired // 로그인 성공에 대한 처리
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired // 로그인실패에 대한 처리
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired // 로그아웃 처리
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired // 접근 권한에 대한 처리
    private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired // 시큐리티 작업에 대한 인터셉터
	private CustomSecurityInterceptor customSecurityInterceptor; 
	
	/**
	 * 스프링 시큐리티의 필터 연결을 설정
	 */
	@Override
	public void configure(WebSecurity web) throws Exception{
		
		// 필터 적용을 제외할 URL 
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/css/**", "/script/**", "/image/**", "/fonts/**");
		web.ignoring().antMatchers("/demo/**");
	}
	
	/**
	 * 인터셉터로 요청을 안전하게 보호하는 방법을 설정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			// Cross site request forgery (사이트간 요청 위조 [사용 권장] )
			//.csrf().disable()
			
			// URL에 따른 권한 체크 
			.authorizeRequests()
				.antMatchers("/", "/index").permitAll()
				.antMatchers("/reload").permitAll()
				.antMatchers("/member/login").permitAll()
				.antMatchers("/member/add").permitAll()
				//.antMatchers("/member/**").hasAnyAuthority("USER")
				//.antMatchers("/admin/login").permitAll()
				//.antMatchers("/admin/security").authenticated()
				//.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
				//.antMatchers("/admin/**").hasRole("ADMIN") // Role은 앞에 "ROlE_" 권한이 붙음
				.anyRequest().authenticated()
			
			// 로그인 설정
			.and()
			.formLogin()
				.loginPage("/member/login") // 로그인 페이지 ( 컨트롤러를 매핑하지 않으면 기본 제공 로그인 페이지 호출 )
				.loginProcessingUrl("/member/login") // 로그인 프로세스 처리할 URL
				.usernameParameter("member_id")
				.passwordParameter("member_pw")
				.successHandler(customAuthenticationSuccessHandler) // 로그인 성공시 이동할 핸들러
				.failureHandler(customAuthenticationFailureHandler) // 로그인 성공시 이동할 핸들러
		
			// 비밀번호 자동저장 설정
			.and()
			.rememberMe()
                .key("remember-me")
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me")
                .userDetailsService(customUserDetailsService)
                .tokenValiditySeconds(86400) // 1일 = 86400초
		
			// 로그아웃 설정
            .and()
            .logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.logoutSuccessHandler(customLogoutSuccessHandler)
		
			// 예외처리 설정
			.and()
			.exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler)
		
			// 필터 설정 (접근할 URL 및 해당 URL에 따른 권한을 확인)
			.and()
			.addFilterBefore(customSecurityInterceptor, FilterSecurityInterceptor.class);
			
			//.httpBasic();
		
			// 세션과 관련된 처리
		   	// .sessionManagement() 
	}
	
	/**
	 * 사용자 세부 서비스를 설정
	 */
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			// 인메모리에 사용자 정보를 세팅
			.inMemoryAuthentication()  
			//.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())  
				//.withUser("abc").password("media!@34").roles("USER")
				.withUser("abc").password("{noop}media!@34").roles("USER")
				.and()
				.withUser("admin").password("{noop}media!@34").roles("USER","ADMIN");
		
			
			 // 비밀번호 인코딩에 대한 제외 처리 
			 // {noop}
			 // NoOpPasswordEncoder
    }*/
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

}
