package com.healthy_plate.ingredient.infrastructure.batch;

import com.healthy_plate.ingredient.domain.model.CsvIngredient;
import com.healthy_plate.ingredient.domain.model.Ingredient;
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
            .resource(new ClassPathResource("data/전국통합식품영양성분정보표준데이터.csv"))
            .encoding("EUC-KR")
            .linesToSkip(1)  // 헤더 스킵
            .delimited()
            .delimiter(",")
            // 모든 컬럼 이름 지정
            .names(
                "col1", "col2", "col3", "col4", "col5",
                "col6", "col7", "col8", "col9", "col10",
                "col11", "col12", "col13", "col14", "col15",
                "col16", "col17", "col18", "col19", "col20",
                "col21", "col22", "col23", "col24", "col25",
                "col26", "col27", "col28", "col29", "col30",
                "col31", "col32", "col33", "col34", "col35",
                "col36", "col37", "col38", "col39", "col40",
                "col41", "col42", "col43", "col44", "col45",
                "col46", "col47"
            )
            // 커스텀 매퍼: 필요한 컬럼만 추출
            .fieldSetMapper(fieldSet -> {
                CsvIngredient ingredient = new CsvIngredient();
                ingredient.setFoodCode(fieldSet.readString(0));      // 1번째: 식품코드
                ingredient.setFoodName(fieldSet.readString(1));      // 2번째: 식품명
                ingredient.setDataTypeCode(fieldSet.readString(2));  // 3번째: 데이터구분코드
                ingredient.setDataTypeName(fieldSet.readString(3));  // 4번째: 데이터구분명
                ingredient.setEnergy(fieldSet.readString(4));        // 5번째: 에너지
                return ingredient;
            })
            .build();
    }

    @Bean
    public ItemProcessor<CsvIngredient, Ingredient> csvProcessor() {
        return csvRow -> {
            try {
                // 식품명 검증
                String foodName = csvRow.getFoodName();
                if (foodName == null || foodName.trim().isEmpty()) {
                    log.warn("식품명이 비어있는 데이터 건너뜀");
                    return null;  // null 반환 시 해당 데이터 스킵
                }

                // 칼로리 파싱
                int calorie = 0;
                try {
                    String energyStr = csvRow.getEnergy();
                    if (energyStr != null && !energyStr.trim().isEmpty()) {
                        calorie = (int) Double.parseDouble(energyStr.trim());
                    }
                } catch (NumberFormatException e) {
                    log.warn("칼로리 파싱 실패 ({}): {}, 기본값 0으로 설정", foodName, csvRow.getEnergy());
                }

                // Ingredient 생성
                return Ingredient.createSystemIngredient(foodName.trim(), calorie);

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