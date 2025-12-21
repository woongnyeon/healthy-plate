package com.healthy_plate.user.domain.repository;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import com.healthy_plate.user.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByProviderAndProviderId(OAuth2Provider provider, String providerId);

    boolean existsByProviderAndProviderId(OAuth2Provider provider, String providerId);

    Optional<User> findByEmail_Value(String email);

    boolean existsByEmail_Value(String email);
}
