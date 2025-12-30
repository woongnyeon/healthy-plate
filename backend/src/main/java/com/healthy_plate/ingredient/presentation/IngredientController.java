package com.healthy_plate.ingredient.presentation;

import com.healthy_plate.ingredient.domain.service.IngredientService;
import com.healthy_plate.ingredient.presentation.dto.IngredientRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Void> createIngredient(@Valid final IngredientRequest request) {
        ingredientService.createIngredient(
            request.name(),
            request.nameEn(),
            request.servingSize(),
            request.unit(),
            request.calorie()
        );
        return ResponseEntity.ok().build();
    }
}
