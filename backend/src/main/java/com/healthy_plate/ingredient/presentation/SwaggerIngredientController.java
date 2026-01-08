package com.healthy_plate.ingredient.presentation;

import com.healthy_plate.ingredient.presentation.dto.IngredientRequest;
import com.healthy_plate.ingredient.presentation.dto.IngredientResponse;
import com.healthy_plate.ingredient.presentation.dto.IngredientUpdateRequest;
import com.healthy_plate.shared.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "식재료", description = "식재료 관련 API")
public interface SwaggerIngredientController {

    @Operation(
        summary = "식재료 생성",
        tags = "식재료",
        description = """
            새로운 식재료를 생성합니다.
            
            **필수 필드:**
            - name: 식재료명 (최대 100자)
            - servingSize: 제공량
            - unit: 단위
            - calorie: 칼로리
            
            **선택 필드:**
            - nameEn: 영문 식재료명 (최대 100자)
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "식재료 생성 성공"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검증 실패)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "이미 존재하는 식재료명",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<Void> create(@Valid @RequestBody IngredientRequest request);

    @Operation(
        summary = "식재료 검색",
        tags = "식재료",
        description = """
            식재료를 이름으로 검색합니다.
            
            **검색 방식:**
            - name 파라미터가 없으면 전체 식재료 목록을 반환합니다.
            - name 파라미터가 있으면 해당 이름을 포함하는 식재료를 검색합니다.
            
            **응답 필드:**
            - name: 식재료명
            - calorie: 칼로리
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "검색 성공",
                content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = IngredientResponse.class))
                )
            )
        }
    )
    ResponseEntity<List<IngredientResponse>> search(@RequestParam(required = false) String name);

    @Operation(
        summary = "식재료 수정",
        tags = "식재료",
        description = """
            기존 식재료 정보를 수정합니다.
            
            **수정 가능한 필드:**
            - name: 식재료명 (최대 100자)
            - nameEn: 영문 식재료명 (최대 100자)
            - servingSize: 제공량
            - unit: 단위
            - calorie: 칼로리
            
            **참고:** 모든 필드는 선택적이며, 제공된 필드만 수정됩니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "식재료 수정 성공"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검증 실패)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 식재료",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<Void> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody IngredientUpdateRequest request
    );

    @Operation(
        summary = "식재료 삭제",
        tags = "식재료",
        description = "식재료를 삭제합니다.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "식재료 삭제 성공"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 식재료",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}