package com.healthy_plate.ingredient.domain.service;

import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.RegistrationType;
import com.healthy_plate.ingredient.domain.repository.IngredientRepository;
import com.healthy_plate.shared.error.exception.BusinessErrorCode;
import com.healthy_plate.shared.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public void createIngredient(
        final String name,
        final String nameEn,
        final String servingSize,
        final String unit,
        final int calorie
    ) {
        if (ingredientRepository.existByName(name)) {
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
}
