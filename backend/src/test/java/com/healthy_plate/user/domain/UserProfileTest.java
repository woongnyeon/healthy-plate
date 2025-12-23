package com.healthy_plate.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.healthy_plate.user.domain.model.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("UserProfile 도메인 테스트")
class UserProfileTest {

    @Test
    @DisplayName("유효한 닉네임과 프로필 이미지 URL, 자기소개로 UserProfile 객체를 생성한다")
    void createUserProfileWithValidValues() {
        // given
        String nickname = "테스트유저";
        String profileImageUrl = "https://example.com/profile.jpg";
        String introduction = "안녕하세요 취미로 요리하는 사람입니다.";

        // when
        UserProfile profile = UserProfile.of(nickname, profileImageUrl, introduction);

        // then
        assertThat(profile.getNickname()).isEqualTo("테스트유저");
        assertThat(profile.getProfileImageUrl()).isEqualTo(profileImageUrl);
    }

    @Test
    @DisplayName("프로필 이미지 URL 없이 UserProfile 객체를 생성한다")
    void createUserProfileWithoutProfileImage() {
        // given
        String nickname = "테스트유저";
        String introduction = "안녕하세요 취미로 요리하는 사람입니다.";

        // when
        UserProfile profile = UserProfile.of(nickname, null, introduction);

        // then
        assertThat(profile.getNickname()).isEqualTo("테스트유저");
        assertThat(profile.getProfileImageUrl()).isNull();
    }

    @Test
    @DisplayName("자기소개 없이 UserProfile 객체를 생성한다")
    void createUserProfileWithoutIntroduction() {
        // given
        String nickname = "테스트유저";
        String profileImageUrl = "https://example.com/profile.jpg";

        // when
        UserProfile profile = UserProfile.of(nickname, profileImageUrl, null);

        // then
        assertThat(profile.getNickname()).isEqualTo("테스트유저");
    }

    @Test
    @DisplayName("닉네임의 공백은 제거된다")
    void nicknameIsTrimmed() {
        // given
        String nicknameWithSpaces = "  테스트유저  ";

        // when
        UserProfile profile = UserProfile.of(nicknameWithSpaces, null, null);

        // then
        assertThat(profile.getNickname()).isEqualTo("테스트유저");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("닉네임이 null이거나 공백이면 예외가 발생한다")
    void throwExceptionWhenNicknameIsNullOrBlank(String invalidNickname) {
        // when & then
        assertThatThrownBy(() -> UserProfile.of(invalidNickname, null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 필수입니다.");
    }

    @Test
    @DisplayName("닉네임이 2자 미만이면 예외가 발생한다")
    void throwExceptionWhenNicknameTooShort() {
        // given
        String shortNickname = "a";

        // when & then
        assertThatThrownBy(() -> UserProfile.of(shortNickname, null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 2-50자 사이여야 합니다.");
    }

    @Test
    @DisplayName("닉네임이 50자를 초과하면 예외가 발생한다")
    void throwExceptionWhenNicknameTooLong() {
        // given
        String longNickname = "a".repeat(51);

        // when & then
        assertThatThrownBy(() -> UserProfile.of(longNickname, null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 2-50자 사이여야 합니다.");
    }

    @Test
    @DisplayName("닉네임이 정확히 2자일 때 UserProfile 객체를 생성한다")
    void createUserProfileWithMinimumNickname() {
        // given
        String minimumNickname = "ab";

        // when
        UserProfile profile = UserProfile.of(minimumNickname, null, null);

        // then
        assertThat(profile.getNickname()).isEqualTo("ab");
    }

    @Test
    @DisplayName("닉네임이 정확히 50자일 때 UserProfile 객체를 생성한다")
    void createUserProfileWithMaximumNickname() {
        // given
        String maximumNickname = "a".repeat(50);

        // when
        UserProfile profile = UserProfile.of(maximumNickname, null, null);

        // then
        assertThat(profile.getNickname()).hasSize(50);
    }

    @Test
    @DisplayName("자기소개가 500자가 넘을 시 예외가 발생한다.")
    void throwExceptionWhenIntroductionTooLong() {
        // given
        String nickName = "chef";
        String maximumIntroduction = "a".repeat(501);

        // when & then
        assertThatThrownBy(() -> UserProfile.of(nickName, null, maximumIntroduction))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("자기소개는 500자 미만이어야 합니다.");
    }


}