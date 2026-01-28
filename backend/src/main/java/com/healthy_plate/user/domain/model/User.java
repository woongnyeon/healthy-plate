package com.healthy_plate.user.domain.model;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "provider_id"})
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    private User(
        final Email email,
        final UserProfile profile,
        final OAuth2Provider provider,
        final String providerId,
        final UserRole role,
        final boolean isActive
    ) {
        this.email = email;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.isActive = isActive;
    }

    public static User createOAuth2User(
        final Email email,
        final OAuth2Provider provider,
        final String providerId
    ) {
        return new User(email, UserProfile.createEmpty(), provider, providerId, UserRole.ROLE_USER, true);
    }

    public static User createOAuth2UserWithProfile(
        final Email email,
        final UserProfile profile,
        final OAuth2Provider provider,
        final String providerId
    ) {
        return new User(email, profile, provider, providerId, UserRole.ROLE_USER, true);
    }

    public static User createAdminUser(
        final Email email,
        final UserProfile profile,
        final OAuth2Provider provider,
        final String providerId
    ) {
        return new User(email, profile, provider, providerId, UserRole.ROLE_ADMIN, true);
    }

    public void updateProfile(final String nickname, final String profileImageUrl, final String introduction) {
        if (this.profile == null) {
            this.profile = UserProfile.createEmpty();
        }
        this.profile.updateNickname(nickname, profileImageUrl, introduction);
    }

    public boolean isFirstLogin() {
        return profile == null || !this.profile.isNicknameSet();
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

}
