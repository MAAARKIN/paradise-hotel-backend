package br.com.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class AuthTokenFilter extends GenericFilterBean {

    private final static String XAUTH_TOKEN_HEADER_NAME = "x-auth-token";
    private TokenHandler tokenHandler;
	
    public AuthTokenFilter(UserDetailsService detailsService, String secret) {
    	this.tokenHandler = new TokenHandler(secret, detailsService);	
	}
    
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(XAUTH_TOKEN_HEADER_NAME);
            if (StringUtils.hasText(authToken)) {
            	//meu algoritmo para buscar meu token
//                String username = this.tokenProvider.getUserNameFromToken(authToken);
                UserDetails details = tokenHandler.parseUserFromToken(authToken);
//                if (this.tokenProvider.validateToken(authToken, details)) {
                if (details != null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

	}

}
