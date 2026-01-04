package com.healthy_plate.ingredient.infrastructure.batch;

import com.healthy_plate.ingredient.domain.model.Calorie;
import com.healthy_plate.ingredient.domain.model.CsvIngredient;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.IngredientName;
import com.healthy_plate.ingredient.domain.model.IngredientUnit;
import com.healthy_plate.ingredient.domain.model.ServingSize;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IngredientBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job importIngredientJob(Step importIngredientStep) {
        return new JobBuilder("importIngredientJob", jobRepository)
            .start(importIngredientStep)
            .build();
    }

    @Bean
    public Step importIngredientStep() {
        return new StepBuilder("importIngredientStep", jobRepository)
            .<CsvIngredient, Ingredient>chunk(1000, transactionManager)
            .reader(csvReader())
            .processor(csvProcessor())
            .writer(dbWriter())
            .build();
    }

    @Bean
    public FlatFileItemReader<CsvIngredient> csvReader() {
        return new FlatFileItemReaderBuilder<CsvIngredient>()
            .name("csvReader")
            .resource(new ClassPathResource("data/ingredients.csv"))
            .encoding("UTF-8")
            .linesToSkip(1)  // 헤더 스킵
            .delimited()
            .delimiter(",")
            .names("name", "servingSize", "unit", "calorie")
            .fieldSetMapper(fieldSet -> new CsvIngredient(
                fieldSet.readString("name"),
                fieldSet.readString("servingSize"),
                fieldSet.readString("unit"),
                fieldSet.readString("calorie")
            ))
            .build();
    }

    @Bean
    public ItemProcessor<CsvIngredient, Ingredient> csvProcessor() {
        return csvRow -> {
            try {
                // 식품명 검증
                String foodName = csvRow.name();
                if (foodName == null || foodName.trim().isEmpty()) {
                    log.warn("식품명이 비어있는 데이터 건너뜀");
                    return null;  // null 반환 시 해당 데이터 스킵
                }

                // 영양성분함량기준량
                String unitStr = csvRow.unit();

                IngredientUnit unit = validateAndParseIngredientUnit(unitStr, foodName);
                Integer calorie = validateAndParseCalorie(csvRow, foodName);
                Double servingSize = validateAndParseServingSize(csvRow, foodName);

                // Value Object 생성
                IngredientName ingredientName = IngredientName.of(foodName.trim());
                Calorie calorieVO = Calorie.of(calorie);
                ServingSize servingSizeVO = ServingSize.of(servingSize);

                // Ingredient 생성
                return Ingredient.createSystemIngredient(ingredientName, calorieVO, servingSizeVO, unit);

            } catch (Exception e) {
                log.error("데이터 처리 중 오류 발생: {}", csvRow, e);
                return null;  // 오류 발생 시 해당 데이터 스킵
            }
        };
    }

    private static Double validateAndParseServingSize(CsvIngredient csvRow, String foodName) {
        double servingSize = 0.0;
        try {
            String servingSizeStr = csvRow.servingSize();
            if (servingSizeStr != null && !servingSizeStr.trim().isEmpty()) {
                servingSize = Double.parseDouble(servingSizeStr.trim());
            }

        } catch (NumberFormatException e) {
            log.warn("제공량 파싱 실패 ({}): {}, 기본값 0으로 설정", foodName, csvRow.servingSize());
        }
        if (servingSize <= 0) {
            log.warn("유효하지 않은 제공량 ({}): {}, 해당 데이터 건너뜀", foodName, servingSize);
            return null;
        }
        return servingSize;
    }

    private static Integer validateAndParseCalorie(CsvIngredient csvRow, String foodName) {
        Integer calorie = 0;
        try {
            String energyStr = csvRow.calorie();
            if (energyStr != null && !energyStr.trim().isEmpty()) {
                calorie = Integer.parseInt(energyStr.trim());
            }
        } catch (NumberFormatException e) {
            log.warn("칼로리 파싱 실패 ({}): {}, 기본값 0으로 설정", foodName, csvRow.calorie());
        }
        return calorie;
    }

    private static IngredientUnit validateAndParseIngredientUnit(String unitStr, String foodName) {
        IngredientUnit unit;
        try {
            unit = IngredientUnit.fromUnit(unitStr.trim());
        } catch (IllegalArgumentException e) {
            log.warn("알 수 없는 단위 ({}): {}, 해당 데이터 건너뜀", foodName, unitStr);
            return null;
        }
        return unit;
    }

    @Bean
    public JpaItemWriter<Ingredient> dbWriter() {
        return new JpaItemWriterBuilder<Ingredient>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }
}