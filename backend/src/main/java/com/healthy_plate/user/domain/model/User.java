package com.healthy_plate.user.domain.model;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import com.healthy_plate.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private UserProfile profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public User(
        final Email email,
        final UserProfile profile,
        final OAuth2Provider provider,
        final String providerId,
        final UserRole role
    ) {
        this.email = email;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public void updateNickname(final String nickname) {
        this.profile.updateNickname(nickname);
    }

    public boolean isFirstLogin() {
        return profile == null || !this.profile.isNicknameSet();
    }

}
