package br.com.backend.service;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.backend.model.User;
import br.com.backend.repository.UserRepository;

@Service
public class UserService {

	@Inject private UserRepository userRepository;
	@Inject private DetailsService detailsService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return detailsService.loadUserByUsername(username);
	}
	
	public void create(User usuario) {
		//validations if possible
		userRepository.save(usuario);
	}
}
