package br.com.backend.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findOneByUsername(String login);
}
