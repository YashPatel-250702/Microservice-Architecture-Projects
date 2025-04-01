package com.tekworks.auth_service.repository;

import com.tekworks.auth_service.entity.UserCerdencials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCerdencialsRepository extends JpaRepository<UserCerdencials, Integer> {

    UserCerdencials findByUsername(String username);
}
