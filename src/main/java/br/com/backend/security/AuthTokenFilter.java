package br.com.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import br.com.backend.exception.AuthTokenException;

public class AuthTokenFilter extends GenericFilterBean {

    private final static String XAUTH_TOKEN_HEADER_NAME = "x-auth-token";
    private TokenHandler tokenHandler;
	
    public AuthTokenFilter(TokenHandler tokenHandler) {
    	this.tokenHandler = tokenHandler;	
	}
    
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String authToken = request.getHeader(XAUTH_TOKEN_HEADER_NAME);
            System.out.println("AuthToken: "+authToken);
            if (StringUtils.hasText(authToken)) {
            	//meu algoritmo para buscar meu token
//                String username = this.tokenProvider.getUserNameFromToken(authToken);
            	UserDetails details = null;
                try {
                	details = tokenHandler.parseUserFromToken(authToken);					
				} catch (Exception e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Auth Token");
					return;
//					throw new AuthTokenException();
				}
//                if (this.tokenProvider.validateToken(authToken, details)) {
                if (details != null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            System.out.println("chamou o dofilter");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new RuntimeException(ex);
        }

	}

}
