package org.generation.italy.repository;

import java.util.Optional;

import org.generation.italy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findByEmail(String email);
}
