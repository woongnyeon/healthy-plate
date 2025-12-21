package com.healthy_plate.user.presentation;

import com.healthy_plate.user.application.UserService;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements SwaggerUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal final Long userId) {
        final User user = userService.findUser(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }
}
