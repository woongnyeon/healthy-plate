package com.healthy_plate.shared.aspect;

import com.healthy_plate.shared.error.exception.AuthenticationErrorCode;
import com.healthy_plate.shared.error.exception.BusinessErrorCode;
import com.healthy_plate.shared.error.exception.BusinessException;
import com.healthy_plate.shared.error.exception.CustomAuthenticationException;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ActiveUserCheckAspect {

    private final UserRepository userRepository;

    /**
     * @throws BusinessException 유저를 찾을 수 없거나 비활성 상태인 경우
     * @RequireActiveUser 어노테이션이 붙은 모든 메서드 실행 전에 호출됩니다.
     */
    @Before("@annotation(com.healthy_plate.shared.annotation.RequireActiveUser)")
    public void checkUserActive() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomAuthenticationException(AuthenticationErrorCode.LOGIN_REQUIRED_SERVICE);
        }

        final Long userId = (Long) authentication.getPrincipal();

        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

        if (!user.is_active()) {
            log.warn("[AOP] 비활성 유저의 작업 시도 - userId: {}, email: {}", userId, user.getEmail());
            throw new BusinessException(BusinessErrorCode.INACTIVE_USER_CANNOT_POST);
        }
    }
}
