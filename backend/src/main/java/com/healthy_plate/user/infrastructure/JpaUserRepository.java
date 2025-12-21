package com.healthy_plate.user.infrastructure;

import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
