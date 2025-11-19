package jp.co.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.task.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	Optional<User> findByName(String name);
	
}

