package br.com.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByName(String name);
}
