package br.com.backend.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController("/user")
public class UserController {
	
	private final Map<String, List<String>> userDb = new HashMap<>();
	@Autowired private UserRepository userRepos;

    public UserController() {
        userDb.put("tom", Arrays.asList("user"));
        userDb.put("sally", Arrays.asList("user", "admin"));
    }

	@RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login)
        throws ServletException {
		
        if (login.name == null || !userDb.containsKey(login.name)) {
            throw new ServletException("Invalid login");
        }
        
        return new LoginResponse(Jwts.builder().setSubject(login.name)
            .claim("roles", userDb.get(login.name)).setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
    }
	
	public static void main(String[] args) {
		String token = Jwts.builder().setSubject("marcos")
				.setId("20")
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey")
				.compact();
		
		System.out.println("Token: " + token);
		
//		final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		System.out.println(Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject());
		System.out.println(Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getExpiration());
		System.out.println(Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getId());
	}
	
	private static class UserLogin {
        public String name;
        public String password;
    }
	
	private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
}
