package com.healthy_plate.ingredient.domain.repository;

import com.healthy_plate.ingredient.domain.model.Ingredient;
import java.util.List;

public interface IngredientRepository {

    // 기본 CRUD 메서드 - JpaRepository와 동일한 시그니처
    Ingredient save(Ingredient ingredient);

    long count();

    void deleteAll();

    // 커스텀 쿼리 메서드
    boolean existsByName(String name);

    List<Ingredient> findByNameContaining(String name);
}
