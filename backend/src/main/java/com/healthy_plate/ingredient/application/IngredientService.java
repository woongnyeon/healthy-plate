package com.healthy_plate.ingredient.application;

import com.healthy_plate.ingredient.application.dto.IngredientWithUserDto;
import com.healthy_plate.ingredient.domain.model.Calorie;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.IngredientName;
import com.healthy_plate.ingredient.domain.model.IngredientUnit;
import com.healthy_plate.ingredient.domain.model.RegistrationType;
import com.healthy_plate.ingredient.domain.model.ServingSize;
import com.healthy_plate.ingredient.domain.repository.IngredientRepository;
import com.healthy_plate.shared.error.exception.BusinessErrorCode;
import com.healthy_plate.shared.error.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public void createIngredient(
        final String name,
        final String nameEn,
        final Double servingSize,
        final String unit,
        final int calorie
    ) {
        if (ingredientRepository.existsByName(name)) {
            throw new BusinessException(BusinessErrorCode.EXIST_INGREDIENT);
        }
        Ingredient ingredient = new Ingredient(
            IngredientName.of(name),
            nameEn,
            Calorie.of(calorie),
            ServingSize.of(servingSize),
            IngredientUnit.fromUnit(unit),
            RegistrationType.SYSTEM,
            true,
            null
        );
        ingredientRepository.save(ingredient);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> searchIngredientsByName(final String name) {
        return ingredientRepository.findByNameContaining(name);
    }

    @Transactional
    public void updateIngredient(
        final Long id,
        final String name,
        final String nameEn,
        final Double servingSize,
        final String unit,
        final Integer calorie
    ) {
        Ingredient ingredient = ingredientRepository.findById(id)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.INGREDIENT_NOT_FOUND));

        ingredient.updateIngredient(
            name != null ? IngredientName.of(name) : null,
            nameEn,
            servingSize != null ? ServingSize.of(servingSize) : null,
            unit != null ? IngredientUnit.fromUnit(unit) : null,
            calorie != null ? Calorie.of(calorie) : null
        );
    }

    @Transactional
    public void deleteById(final Long id) {
        ingredientRepository.deleteById(id);
    }

    /**
     * CQRS - Query: 특정 사용자가 등록한 식재료 목록 조회 (User 정보 포함)
     * 읽기 전용 DTO를 반환하여 User 정보와 함께 효율적으로 조회
     */
    @Transactional(readOnly = true)
    public List<IngredientWithUserDto> getIngredientsByUserId(final Long userId) {
        return ingredientRepository.findByUserIdWithUserInfo(userId);
    }
}
