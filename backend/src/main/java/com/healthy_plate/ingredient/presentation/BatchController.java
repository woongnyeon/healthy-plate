package com.healthy_plate.ingredient.presentation;

import com.healthy_plate.ingredient.domain.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job importIngredientJob;
    private final IngredientRepository ingredientRepository;

    /**
     * 식품 데이터 로딩 배치 Job 실행
     * POST /api/batch/ingredients/load
     */
    @PostMapping("/ingredients/load")
    public ResponseEntity<Map<String, Object>> loadIngredientData() {
        Map<String, Object> response = new HashMap<>();

        try {
            // 이미 데이터가 있는지 확인
            long existingCount = ingredientRepository.count();
            if (existingCount > 0) {
                log.warn("이미 재료 데이터가 {}개 존재합니다.", existingCount);
                response.put("success", false);
                response.put("message", "이미 데이터가 존재합니다.");
                response.put("existingCount", existingCount);
                return ResponseEntity.ok(response);
            }

            log.info("재료 데이터 로딩 배치 시작...");

            // Job 파라미터 생성 (매번 다른 파라미터로 실행)
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            // Job 실행
            JobExecution jobExecution = jobLauncher.run(importIngredientJob, jobParameters);

            // 결과 수집
            long readCount = 0;
            long writeCount = 0;
            long skipCount = 0;

            for (var stepExecution : jobExecution.getStepExecutions()) {
                readCount += stepExecution.getReadCount();
                writeCount += stepExecution.getWriteCount();
                skipCount += stepExecution.getSkipCount();

                log.info("Step: {} - Read: {}, Write: {}, Skip: {}",
                    stepExecution.getStepName(),
                    stepExecution.getReadCount(),
                    stepExecution.getWriteCount(),
                    stepExecution.getSkipCount()
                );
            }

            log.info("재료 데이터 로딩 완료! Status: {}", jobExecution.getStatus());

            // 응답 구성
            response.put("success", true);
            response.put("message", "데이터 로딩 완료");
            response.put("status", jobExecution.getStatus().toString());
            response.put("readCount", readCount);
            response.put("writeCount", writeCount);
            response.put("skipCount", skipCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("배치 실행 중 오류 발생", e);
            response.put("success", false);
            response.put("message", "배치 실행 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}