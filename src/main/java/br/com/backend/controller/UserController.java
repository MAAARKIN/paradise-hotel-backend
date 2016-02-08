package br.com.backend.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.model.User;
import br.com.backend.security.AuthoritiesConstants;
import br.com.backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {
	
	private static final int ONE_HOUR = 1;
	private final Map<String, List<String>> userDb = new HashMap<>();
	@Inject private UserService userService;
	

    public UserController() {
        userDb.put("tom", Arrays.asList("user"));
        userDb.put("sally", Arrays.asList("user", "admin"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@Valid @RequestBody User usuario) {
    	usuario.addAuthority(AuthoritiesConstants.ANONYMOUS);
    	usuario.addAuthority(AuthoritiesConstants.USER);
    	System.out.println(usuario.toString());
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
	@RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody final UserLogin login) throws ServletException {
		
		System.out.println(login.name + " "+ login.password);
		UserDetails details = userService.loadUserByUsername(login.name);
		System.out.println(details);
		
		if (details == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		DateTime now = new DateTime().plusHours(ONE_HOUR);
		
        LoginResponse response = new LoginResponse(Jwts.builder().setSubject(details.getUsername())
        	.claim("roles", details.getAuthorities())
        	.setIssuedAt(new Date())
        	.setId(UUID.randomUUID().toString())
        	.setExpiration(now.toDate())
        	.setHeaderParam("typ", "JWT")
            .signWith(SignatureAlgorithm.HS256, "secret").compact());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@RequestMapping(value = "teste", method = RequestMethod.GET)
	public ResponseEntity<String> teste() {
		
		return new ResponseEntity<>("Sucesso Permissao", HttpStatus.OK);
	}
	
	
//	public static void main(String[] args) {
//		String token = Jwts.builder().setSubject("marcos")
//				.setId("20")
//				.setIssuedAt(new Date())
//				.signWith(SignatureAlgorithm.HS256, "testeSecret")
//				.compact();
//		
//		System.out.println("Token: " + token);
//		
////		final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
////		Jwts.parser().setSigningKey("testeSecret").parseClaimsJws(token).getBody().get
//		System.out.println(Jwts.parser().setSigningKey("testeSecret").parseClaimsJws(token).getBody().getSubject());
//		System.out.println(Jwts.parser().setSigningKey("testeSecret").parseClaimsJws(token).getBody().getExpiration());
//		System.out.println(Jwts.parser().setSigningKey("testeSecret").parseClaimsJws(token).getBody().getId());
//	}
	
	private static class UserLogin {
		@NotBlank @NotNull
        public String name;
		@NotBlank @NotNull
        public String password;
    }
	
	private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
        	System.out.println(token);
            this.token = token;
        }
    }
}
