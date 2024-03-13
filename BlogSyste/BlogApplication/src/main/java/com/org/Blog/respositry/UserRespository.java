package com.org.Blog.respositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.Blog.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

//	@Query("select e.roleid from user_roles e where e.userId='userId'")
//	  Integer getUserId(@Param(value = "userId") Long userId);
}
