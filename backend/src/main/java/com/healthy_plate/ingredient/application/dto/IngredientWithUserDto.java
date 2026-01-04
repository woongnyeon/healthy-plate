package com.healthy_plate.ingredient.application.dto;

/**
 * 읽기 전용 DTO - 식재료 정보 + 등록자 정보
 * CQRS Query 모델
 */
public record IngredientWithUserDto(
    Long ingredientId,
    String ingredientName,
    String ingredientNameEn,
    Integer calorie,
    Double servingSize,
    String unit,
    String registrationType,
    Boolean isVerified,
    Long registeredUserId,
    String registeredUserNickname
) {

    public static IngredientWithUserDto of(
        final Long ingredientId,
        final String ingredientName,
        final String ingredientNameEn,
        final Integer calorie,
        final Double servingSize,
        final String unit,
        final String registrationType,
        final Boolean isVerified,
        final Long registeredUserId,
        final String registeredUserNickname
    ) {
        return new IngredientWithUserDto(
            ingredientId,
            ingredientName,
            ingredientNameEn,
            calorie,
            servingSize,
            unit,
            registrationType,
            isVerified,
            registeredUserId,
            registeredUserNickname
        );
    }
}