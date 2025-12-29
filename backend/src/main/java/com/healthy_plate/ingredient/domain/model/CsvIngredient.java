package com.healthy_plate.ingredient.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CSV 파일의 한 행을 매핑하는 DTO
 * Spring Batch의 FlatFileItemReader가 사용
 *
 * CSV 컬럼 순서: 식품코드, 식품명, 데이터구분코드, 데이터구분명, 에너지, ...
 * 필요한 것만: 식품명(2번째), 에너지(5번째)
 */
@Getter
@Setter
@NoArgsConstructor
public class CsvIngredient {

    // CSV 순서대로 매핑 (필요 없는 필드도 선언)
    private String foodCode;           // 식품코드 (1)
    private String foodName;           // 식품명 (2) ← 사용
    private String dataTypeCode;       // 데이터구분코드 (3)
    private String dataTypeName;       // 데이터구분명 (4)
    private String energy;             // 에너지 (5) ← 사용
}