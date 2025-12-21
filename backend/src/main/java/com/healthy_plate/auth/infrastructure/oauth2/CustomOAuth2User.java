package com.healthy_plate.auth.infrastructure.oauth2;

import com.healthy_plate.user.domain.model.UserRole;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Long userId;
    private final String email;
    private final UserRole role;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(
        final Long userId,
        final String email,
        final UserRole role,
        final Map<String, Object> attributes
    ) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getName() {
        return email;
    }
}
