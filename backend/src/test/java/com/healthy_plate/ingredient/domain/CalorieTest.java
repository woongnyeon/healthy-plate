package com.healthy_plate.ingredient.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.healthy_plate.ingredient.domain.model.Calorie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("칼로리 도메인 테스트")
public class CalorieTest {

    @ParameterizedTest
    @ValueSource(ints = {100, 0, 3200})
    @DisplayName("유효한 칼로리로 Calorie 객체를 생성한다")
    void createCalorieWithValidValue(Integer testCalorie) {

        //when
        Calorie calorie = Calorie.of(testCalorie);

        //then
        assertThat(calorie.getValue()).isEqualTo(testCalorie);
    }

    @DisplayName("0 미만의 칼로리는 예외가 발생한다")
    void throwExceptionWhenCalorieLessThan0() {

        //given
        Integer testCalorie = -1;

        //when & then
        assertThatThrownBy(() -> Calorie.of(testCalorie))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("칼로리는 음수일 수 없습니다.");
    }

}
