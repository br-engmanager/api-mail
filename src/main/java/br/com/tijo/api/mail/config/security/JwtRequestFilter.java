package br.com.tijo.api.mail.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.tijo.api.jwtsecuritytools.RouteException;
import br.com.tijo.api.jwtsecuritytools.SecurityService;



@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private SecurityService securityService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

			throws ServletException, IOException {

		List<RouteException> exceptions = new ArrayList<RouteException>();
		exceptions.add(new RouteException("*", "OPTIONS"));
		
		securityService.initializeFilter(request, response, chain, null, exceptions);

		chain.doFilter(request, response);

	}



}