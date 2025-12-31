package com.healthy_plate.ingredient.infrastructure.batch;

import com.healthy_plate.ingredient.domain.model.CsvIngredient;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.model.IngredientUnit;
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
                String servingSize = csvRow.servingSize();
                String unitStr = csvRow.unit();

                // 단위 변환
                IngredientUnit unit;
                try {
                    unit = IngredientUnit.fromUnit(unitStr.trim());
                } catch (IllegalArgumentException e) {
                    log.warn("알 수 없는 단위 ({}): {}, 해당 데이터 건너뜀", foodName, unitStr);
                    return null;  // 알 수 없는 단위는 스킵
                }

                // 칼로리 파싱
                int calorie = 0;
                try {
                    String energyStr = csvRow.calorie();
                    if (energyStr != null && !energyStr.trim().isEmpty()) {
                        calorie = (int) Double.parseDouble(energyStr.trim());
                    }
                } catch (NumberFormatException e) {
                    log.warn("칼로리 파싱 실패 ({}): {}, 기본값 0으로 설정", foodName, csvRow.calorie());
                }

                // Ingredient 생성
                return Ingredient.createSystemIngredient(foodName.trim(), calorie, Double.valueOf(servingSize.trim()), unit);

            } catch (Exception e) {
                log.error("데이터 처리 중 오류 발생: {}", csvRow, e);
                return null;  // 오류 발생 시 해당 데이터 스킵
            }
        };
    }

    @Bean
    public JpaItemWriter<Ingredient> dbWriter() {
        return new JpaItemWriterBuilder<Ingredient>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }
}