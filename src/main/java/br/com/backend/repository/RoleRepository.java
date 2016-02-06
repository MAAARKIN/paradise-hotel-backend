package br.com.backend.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.backend.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
