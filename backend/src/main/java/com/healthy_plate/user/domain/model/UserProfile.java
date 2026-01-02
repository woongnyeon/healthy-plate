package com.healthy_plate.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Column(unique = true)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(length = 500)
    private String introduction;

    private UserProfile(final String nickname, final String profileImageUrl, final String introduction) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public static UserProfile of(final String nickname, final String profileImageUrl, final String introduction) {
        validateName(nickname);
        validateIntroduction(introduction);
        return new UserProfile(nickname.trim(), profileImageUrl, introduction);
    }

    public static UserProfile createEmpty() {
        return new UserProfile(null, null, null);
    }

    public void updateNickname(final String nickname, final String profileImageUrl, final String introduction) {
        validateName(nickname);
        validateIntroduction(introduction);
        this.nickname = nickname.trim();
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    @JsonIgnore
    public boolean isNicknameSet() {
        return this.nickname != null;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new IllegalArgumentException("이름은 2-50자 사이여야 합니다.");
        }
    }

    private static void validateIntroduction(String introduction) {
        if (introduction != null) {
            if (introduction.length() > 500) {
                throw new IllegalArgumentException("자기소개는 500자 미만이어야 합니다.");
            }
        }
    }
}
