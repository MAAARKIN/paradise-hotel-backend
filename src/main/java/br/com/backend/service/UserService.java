package br.com.backend.service;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Inject private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(login);
	}
}
