package com.simpolor.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.simpolor.app.security.CustomAccessDeniedHandler;
import com.simpolor.app.security.CustomLogoutSuccessHandler;
import com.simpolor.app.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired // 시큐리티 작업에 대한 인터셉터
	private CustomSecurityInterceptor customSecurityInterceptor; 
	
	@Autowired // 로그인에 대한 처리
	private CustomAuthenticationProvider customAuthProvider; 
	
	@Autowired // 로그인 성공에 대한 처리
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired // 로그인실패에 대한 처리
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired // 접근 권한에 대한 처리
    private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	UserService userService;
	
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
			.csrf().disable()
			
			// URL에 따른 권한 체크 
			.authorizeRequests()
				.antMatchers("/user/login").permitAll()
				.antMatchers("/user/add").permitAll()
				.antMatchers("/user/**").hasAnyAuthority("USER")
				.antMatchers("/admin/login").permitAll()
				.antMatchers("/admin/security").authenticated()
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
				//.antMatchers("/admin/**").hasRole("ADMIN") // Role은 앞에 "ROlE_" 권한이 붙음
				.anyRequest().authenticated()
			
			// 로그인 설정
			.and()
			.formLogin()
				.loginPage("/user/login") // 로그인 페이지 ( 컨트롤러를 매핑하지 않으면 기본 제공 로그인 페이지 호출 )
				.loginProcessingUrl("/user/login") // 로그인 프로세스 처리할 URL
				.successHandler(customAuthenticationSuccessHandler) // 로그인 성공시 이동할 핸들러
				.failureHandler(customAuthenticationFailureHandler) // 로그인 성공시 이동할 핸들러
				.usernameParameter("user_id")
				.passwordParameter("user_pw")
		
			// 비밀번호 자동저장 설정
			.and()
			.rememberMe()
                .key("remember-me")
                .rememberMeParameter("remember-me")
                //.rememberMeCookieName("remember-me")
                .tokenValiditySeconds(86400) // 1일 = 86400초
		
			// 로그아웃 설정
            .and()
            .logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
				//.and()
		
			//.httpBasic();
		
			// 예외처리 설정
			//.exceptionHandling()
				//.accessDeniedPage("/access/denied");
				//.accessDeniedHandler(customAccessDeniedHandler);
		
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
		auth.userDetailsService(userService).passwordEncoder(userService.passwordEncoder());
	}

}