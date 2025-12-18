package com.healthy_plate.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Email 도메인 테스트")
class EmailTest {

    @Test
    @DisplayName("유효한 이메일로 Email 객체를 생성한다")
    void createEmailWithValidValue() {
        // given
        String validEmail = "test@example.com";

        // when
        Email email = Email.of(validEmail);

        // then
        assertThat(email.getValue()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("이메일은 소문자로 변환되어 저장된다")
    void emailIsConvertedToLowerCase() {
        // given
        String upperCaseEmail = "TEST@EXAMPLE.COM";

        // when
        Email email = Email.of(upperCaseEmail);

        // then
        assertThat(email.getValue()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("이메일의 공백은 제거된다")
    void emailIsTrimmed() {
        // given
        String emailWithSpaces = "  test@example.com  ";

        // when
        Email email = Email.of(emailWithSpaces);

        // then
        assertThat(email.getValue()).isEqualTo("test@example.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("이메일이 null이거나 공백이면 예외가 발생한다")
    void throwExceptionWhenEmailIsNullOrBlank(String invalidEmail) {
        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 필수입니다.");
    }
}