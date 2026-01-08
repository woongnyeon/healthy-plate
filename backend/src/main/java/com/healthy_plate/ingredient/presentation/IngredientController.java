package com.healthy_plate.ingredient.presentation;

import com.healthy_plate.ingredient.application.IngredientService;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.presentation.dto.IngredientRequest;
import com.healthy_plate.ingredient.presentation.dto.IngredientResponse;
import com.healthy_plate.ingredient.presentation.dto.IngredientUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController implements SwaggerIngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody final IngredientRequest request) {
        ingredientService.createIngredient(
            request.name(),
            request.nameEn(),
            request.servingSize(),
            request.unit(),
            request.calorie()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> search(@RequestParam(required = false) String name) {
        List<Ingredient> ingredients = ingredientService.searchIngredientsByName(name);
        List<IngredientResponse> responses = ingredients.stream()
            .map(IngredientResponse::from)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable("id") final Long id,
        @Valid @RequestBody final IngredientUpdateRequest request
    ) {
        ingredientService.updateIngredient(
            id,
            request.name(),
            request.nameEn(),
            request.servingSize(),
            request.unit(),
            request.calorie()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
