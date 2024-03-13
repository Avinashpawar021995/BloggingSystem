package com.org.Blog.respositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.Blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
