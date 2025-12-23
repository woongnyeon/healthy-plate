package com.healthy_plate.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Column
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(length = 500)
    private String introduction;


    public static UserProfile of(String nickname, String profileImageUrl, String introduction) {
        validateName(nickname);
        validateIntroduction(introduction);
        UserProfile profile = new UserProfile();
        profile.nickname = nickname.trim();
        profile.profileImageUrl = profileImageUrl;
        profile.introduction = introduction;
        return profile;
    }

    public static UserProfile createEmpty() {
        UserProfile profile = new UserProfile();
        profile.nickname = null;
        profile.profileImageUrl = null;
        profile.introduction = null;
        return profile;
    }

    public void updateNickname(String nickname) {
        validateName(nickname);
        this.nickname = nickname.trim();
    }

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
