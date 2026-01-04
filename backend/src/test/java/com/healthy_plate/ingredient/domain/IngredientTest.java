package com.healthy_plate.ingredient.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.healthy_plate.ingredient.domain.model.Calorie;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.IngredientName;
import com.healthy_plate.ingredient.domain.model.IngredientUnit;
import com.healthy_plate.ingredient.domain.model.RegistrationType;
import com.healthy_plate.ingredient.domain.model.ServingSize;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Ingredient 도메인 테스트")
class IngredientTest {

    @ParameterizedTest
    @ValueSource(strings = {"당근", "carrot", "북어국", "마라탕"})
    @DisplayName("유효한 값으로 Ingredient 객체를 생성한다")
    void createIngredientWithValidValues(String testName) {
        // given
        IngredientName name = IngredientName.of(testName);
        String nameEn = null;
        Calorie calorie = Calorie.of(41);
        ServingSize servingSize = ServingSize.of(100.0);
        IngredientUnit unit = IngredientUnit.G;
        RegistrationType registrationType = RegistrationType.SYSTEM;

        // when
        Ingredient ingredient = new Ingredient(
            name,
            null,
            calorie,
            servingSize,
            unit,
            registrationType,
            true,
            null
        );

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getName()).isEqualTo(name);
            softly.assertThat(ingredient.getNameEn()).isNull();
            softly.assertThat(ingredient.getCalorie()).isEqualTo(calorie);
            softly.assertThat(ingredient.getServingSize()).isEqualTo(servingSize);
            softly.assertThat(ingredient.getUnit()).isEqualTo(unit);
            softly.assertThat(ingredient.getRegistrationType()).isEqualTo(registrationType);
            softly.assertThat(ingredient.getIsVerified()).isTrue();
            softly.assertThat(ingredient.getRegisterId()).isNull();
        });
    }


    @Test
    @DisplayName("시스템 식재료를 생성한다 (Batch용)")
    void createSystemIngredient() {
        // given
        IngredientName name = IngredientName.of("쌀");
        Calorie calorie = Calorie.of(130);
        ServingSize servingSize = ServingSize.of(100.0);
        IngredientUnit unit = IngredientUnit.G;

        // when
        Ingredient ingredient = Ingredient.createSystemIngredient(name, calorie, servingSize, unit);

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getName()).isEqualTo(name);
            softly.assertThat(ingredient.getNameEn()).isNull();
            softly.assertThat(ingredient.getCalorie()).isEqualTo(calorie);
            softly.assertThat(ingredient.getServingSize()).isEqualTo(servingSize);
            softly.assertThat(ingredient.getUnit()).isEqualTo(unit);
            softly.assertThat(ingredient.getRegistrationType()).isEqualTo(RegistrationType.SYSTEM);
            softly.assertThat(ingredient.getIsVerified()).isTrue();
            softly.assertThat(ingredient.getRegisterId()).isNull();
        });
    }

    @Test
    @DisplayName("식재료 정보를 업데이트한다")
    void updateIngredient() {
        // given
        Ingredient ingredient = Ingredient.createSystemIngredient(
            IngredientName.of("당근"),
            Calorie.of(41),
            ServingSize.of(100.0),
            IngredientUnit.G
        );

        IngredientName newName = IngredientName.of("당근(데친것)");
        String newNameEn = "Boiled Carrot";
        ServingSize newServingSize = ServingSize.of(150.0);
        IngredientUnit newUnit = IngredientUnit.G;
        Calorie newCalorie = Calorie.of(35);

        // when
        ingredient.updateIngredient(newName, newNameEn, newServingSize, newUnit, newCalorie);

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getName()).isEqualTo(newName);
            softly.assertThat(ingredient.getNameEn()).isEqualTo(newNameEn);
            softly.assertThat(ingredient.getServingSize()).isEqualTo(newServingSize);
            softly.assertThat(ingredient.getUnit()).isEqualTo(newUnit);
            softly.assertThat(ingredient.getCalorie()).isEqualTo(newCalorie);
        });
    }

    @Test
    @DisplayName("식재료 이름만 업데이트한다")
    void updateIngredientNameOnly() {
        // given
        IngredientName originalName = IngredientName.of("당근");
        Calorie originalCalorie = Calorie.of(41);
        Ingredient ingredient = Ingredient.createSystemIngredient(
            originalName,
            originalCalorie,
            ServingSize.of(100.0),
            IngredientUnit.G
        );

        IngredientName newName = IngredientName.of("당근(생것)");

        // when
        ingredient.updateIngredient(newName, null, null, null, null);

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getName()).isEqualTo(newName);
            softly.assertThat(ingredient.getCalorie()).isEqualTo(originalCalorie);
        });
    }

    @ParameterizedTest
    @MethodSource("provideIngredientUnits")
    @DisplayName("다양한 단위로 식재료를 생성한다")
    void createIngredientWithVariousUnits(
        String ingredientName,
        Integer calorieValue,
        Double servingSizeValue,
        IngredientUnit unit,
        String expectedUnit,
        String expectedDescription
    ) {
        // given
        IngredientName name = IngredientName.of(ingredientName);
        Calorie calorie = Calorie.of(calorieValue);
        ServingSize servingSize = ServingSize.of(servingSizeValue);

        // when
        Ingredient ingredient = Ingredient.createSystemIngredient(name, calorie, servingSize, unit);

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getUnit()).isEqualTo(unit);
            softly.assertThat(ingredient.getUnit().getUnit()).isEqualTo(expectedUnit);
            softly.assertThat(ingredient.getUnit().getDescription()).isEqualTo(expectedDescription);
        });
    }

    static Stream<Arguments> provideIngredientUnits() {
        return Stream.of(
            Arguments.of("소고기", 250, 100.0, IngredientUnit.G, "g", "그램"),
            Arguments.of("우유", 60, 200.0, IngredientUnit.ML, "ml", "밀리리터"),
            Arguments.of("계란", 70, 1.0, IngredientUnit.EA, "개", "개"),
            Arguments.of("밀가루", 364, 1.0, IngredientUnit.KG, "kg", "킬로그램"),
            Arguments.of("물", 0, 1.0, IngredientUnit.L, "L", "리터"),
            Arguments.of("간장", 10, 1.0, IngredientUnit.TBSP, "큰술", "큰술"),
            Arguments.of("소금", 0, 1.0, IngredientUnit.TSP, "작은술", "작은술"),
            Arguments.of("쌀", 130, 1.0, IngredientUnit.CUP, "컵", "컵")
        );
    }

    @Test
    @DisplayName("USER 타입으로 식재료를 생성한다")
    void createUserTypeIngredient() {
        // given
        IngredientName name = IngredientName.of("직접 등록 식재료");
        Calorie calorie = Calorie.of(100);
        ServingSize servingSize = ServingSize.of(50.0);

        // when
        Ingredient ingredient = new Ingredient(
            name,
            null,
            calorie,
            servingSize,
            IngredientUnit.G,
            RegistrationType.USER,
            false,
            null
        );

        // then
        assertSoftly(softly -> {
            softly.assertThat(ingredient.getRegistrationType()).isEqualTo(RegistrationType.USER);
            softly.assertThat(ingredient.getIsVerified()).isFalse();
        });
    }

    @Test
    @DisplayName("칼로리가 0인 식재료를 생성한다")
    void createIngredientWithZeroCalorie() {
        // given
        IngredientName name = IngredientName.of("물");
        Calorie calorie = Calorie.of(0);
        ServingSize servingSize = ServingSize.of(200.0);

        // when
        Ingredient ingredient = Ingredient.createSystemIngredient(
            name,
            calorie,
            servingSize,
            IngredientUnit.ML
        );

        // then
        assertThat(ingredient.getCalorie().getValue()).isEqualTo(0);
    }
}