package com.healthy_plate.ingredient.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.healthy_plate.ingredient.domain.model.IngredientName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("IngredientName 도메인 테스트")
class IngredientNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"당근", "Carrot", "당근(데친것)", "죽순_순_데친것", "a"})
    @DisplayName("유효한 식재료명으로 IngredientName 객체를 생성한다")
    void createIngredientNameWithValidValue(String testName) {
        // when
        IngredientName ingredientName = IngredientName.of(testName);

        // then
        assertThat(ingredientName.getValue()).isEqualTo(testName);
    }

    @Test
    @DisplayName("100자 식재료명으로 IngredientName 객체를 생성한다")
    void createIngredientNameWithMaxLength() {
        // given
        String maxLengthName = "a".repeat(100);

        // when
        IngredientName ingredientName = IngredientName.of(maxLengthName);

        // then
        assertThat(ingredientName.getValue()).hasSize(100);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("식재료명이 null이거나 공백이면 예외가 발생한다")
    void throwExceptionWhenNameIsNullOrBlank(String invalidName) {
        // when & then
        assertThatThrownBy(() -> IngredientName.of(invalidName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("식재료명은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("식재료명이 100자를 초과하면 예외가 발생한다")
    void throwExceptionWhenNameTooLong() {
        // given
        String tooLongName = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> IngredientName.of(tooLongName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("식재료명은 100자 이하여야 합니다.");
    }
}