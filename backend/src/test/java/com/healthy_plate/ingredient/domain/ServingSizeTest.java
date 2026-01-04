package com.healthy_plate.ingredient.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.healthy_plate.ingredient.domain.model.ServingSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ServingSize 도메인 테스트")
class ServingSizeTest {

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1.0, 50.0, 100.0, 500.0, 1000.0})
    @DisplayName("유효한 1회 제공량으로 ServingSize 객체를 생성한다")
    void createServingSizeWithValidValue(Double testServingSize) {
        // when
        ServingSize servingSize = ServingSize.of(testServingSize);

        // then
        assertThat(servingSize.getValue()).isEqualTo(testServingSize);
    }

    @Test
    @DisplayName("null 값으로 ServingSize 객체를 생성한다")
    void createServingSizeWithNull() {
        // when
        ServingSize servingSize = ServingSize.of(null);

        // then
        assertThat(servingSize.getValue()).isNull();
    }

    @Test
    @DisplayName("1회 제공량이 0이면 예외가 발생한다")
    void throwExceptionWhenServingSizeIsZero() {
        // given
        Double zeroServingSize = 0.0;

        // when & then
        assertThatThrownBy(() -> ServingSize.of(zeroServingSize))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("1회 제공량은 0보다 커야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -1.0, -100.0})
    @DisplayName("1회 제공량이 음수이면 예외가 발생한다")
    void throwExceptionWhenServingSizeIsNegative(Double negativeServingSize) {
        // when & then
        assertThatThrownBy(() -> ServingSize.of(negativeServingSize))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("1회 제공량은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("소수점 1회 제공량으로 ServingSize 객체를 생성한다")
    void createServingSizeWithDecimal() {
        // given
        Double decimalServingSize = 12.5;

        // when
        ServingSize servingSize = ServingSize.of(decimalServingSize);

        // then
        assertThat(servingSize.getValue()).isEqualTo(12.5);
    }
}