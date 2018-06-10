package com.shop.repository;

import com.shop.domain.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    void deleteUserRoleByUserId(int userId);
}
