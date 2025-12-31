package com.healthy_plate.ingredient.application;

import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.RegistrationType;
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
            name,
            nameEn,
            calorie,
            servingSize,
            unit,
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
            name,
            nameEn,
            servingSize,
            unit,
            calorie
        );
    }

    @Transactional
    public void deleteById(final Long id) {
        ingredientRepository.deleteById(id);
    }
}
