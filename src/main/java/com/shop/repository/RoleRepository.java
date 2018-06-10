package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.domain.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
