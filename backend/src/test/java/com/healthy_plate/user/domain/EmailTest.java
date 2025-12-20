package com.healthy_plate.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.healthy_plate.user.domain.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Email 도메인 테스트")
class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "test.name@example.co.kr", "test+tag@example.com"})
    @DisplayName("유효한 이메일로 Email 객체를 생성한다")
    void createEmailWithValidValue(String testEmail) {

        //when
        Email email = Email.of(testEmail);

        //then
        assertThat(email.getValue().equals(testEmail));
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

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "test@", "@example.com", "test@.com", "test @example.com"})
    @DisplayName("잘못된 이메일 형식이면 예외가 발생한다")
    void throwExceptionWhenEmailFormatIsInvalid(String invalidEmail) {
        assertThatThrownBy(() -> Email.of(invalidEmail))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바른 이메일 형식이 아닙니다.");
    }

}