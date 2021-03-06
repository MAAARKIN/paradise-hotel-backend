package br.com.backend.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.backend.security.AuthTokenFilter;
import br.com.backend.security.Http401UnauthorizedEntryPoint;
import br.com.backend.security.TokenHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject private Http401UnauthorizedEntryPoint authenticationEntryPoint;
	@Inject private TokenHandler tokenHandler;
	@Value("${secret}") private String secret;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/users/login").permitAll()
            .antMatchers("/users/").permitAll()
            .antMatchers("/users/**").authenticated()
//            .antMatchers("/api/register").permitAll()
//            .antMatchers("/api/activate").permitAll()
//            .antMatchers("/api/authenticate").permitAll()
//            .antMatchers("/api/account/reset_password/init").permitAll()
//            .antMatchers("/api/account/reset_password/finish").permitAll()
//            .antMatchers("/api/**").authenticated()
//            .antMatchers("/metrics/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS)
//            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/mappings/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/liquibase/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/v2/api-docs/**").permitAll()
//            .antMatchers("/configuration/security").permitAll()
//            .antMatchers("/configuration/ui").permitAll()
//            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/protected/**").authenticated() 
        .and()
        	.addFilterBefore(new AuthTokenFilter(tokenHandler), UsernamePasswordAuthenticationFilter.class);

    }
	
}
