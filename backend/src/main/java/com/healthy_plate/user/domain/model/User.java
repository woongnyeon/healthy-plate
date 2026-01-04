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

    @Column(nullable = false)
    private boolean is_active;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    public User(
        final Email email,
        final UserProfile profile,
        final OAuth2Provider provider,
        final String providerId,
        final UserRole role,
        final boolean is_active
    ) {
        this.email = email;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.is_active = is_active;
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
