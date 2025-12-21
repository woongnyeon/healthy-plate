package com.healthy_plate.auth.infrastructure.repository;

import com.healthy_plate.auth.domain.model.RefreshToken;
import com.healthy_plate.auth.domain.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepository {

}
