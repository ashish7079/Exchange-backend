package com.example.demo.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.AuthService;
import com.example.demo.util.Jwtutil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Jwtfilter extends OncePerRequestFilter{
	
	  @Autowired
	    private Jwtutil jwtutil; 

	    @Autowired
	    private AuthService service;
	
	    @Override
	    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
            FilterChain filter) throws ServletException,IOException {
	    	 
	    	String header = request.getHeader("Authorization");
	    	String token = null;
	    	String email = null;
	    	
	    	if(header != null && header.startsWith("Bearer ")) {
	    		
	    		token = header.substring(7);
	    		email = jwtutil.extractemail(token);
	    		
	    		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    			UserDetails userdetails = service.loadUserByUsername(email);
	    			
	    			 if ( jwtutil.validateToken( token,userdetails.getUsername() )) {
	    				 
	    				 UsernamePasswordAuthenticationToken authToken =
	    					        new UsernamePasswordAuthenticationToken(
	    					                userdetails,
	    					                null,
	    					                userdetails.getAuthorities()
	    					        );
//	    				 	
//	    		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//	    				userdetails, null,authorities);

	                     authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                     SecurityContextHolder.getContext().setAuthentication( authToken);
	    		}
	    	}
	    	
	    }
	    	filter.doFilter(request,response);
	    }
}
