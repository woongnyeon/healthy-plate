package com.healthy_plate.ingredient.presentation;

import com.healthy_plate.ingredient.domain.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

}
