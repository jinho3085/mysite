package com.bit2025.mysite.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	   @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
	    }
	   
	   @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	        .formLogin(formLogin -> {
	        	formLogin
	        		.loginPage("/user/login")
	        		.loginProcessingUrl("/user/auth")
	        		.usernameParameter("email")
	        		.passwordParameter("password")
	        		.defaultSuccessUrl("/");
	        	
	        })
	    	.authorizeHttpRequests(authorizeRequest -> {
	    		/* ACL */
	    		authorizeRequest
	    			.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null)).authenticated()
	    			.requestMatchers(new RegexRequestMatcher("^/user/update$", null)).authenticated()
		    		.requestMatchers(new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$", null)).authenticated()
	    			.anyRequest().permitAll();
	    		
	    	});
	        
	        return http.build();
	    }
}
