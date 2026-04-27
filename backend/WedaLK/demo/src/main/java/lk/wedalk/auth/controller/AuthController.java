package lk.wedalk.auth.controller;

import jakarta.validation.Valid;
import lk.wedalk.auth.dto.AuthResponse;
import lk.wedalk.auth.dto.LoginRequest;
import lk.wedalk.auth.dto.RegisterRequest;
import lk.wedalk.auth.service.AuthService;
import lk.wedalk.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponse>> register(
      @Valid @RequestBody RegisterRequest request) {
    log.info("Registration attempt for email='{}'", request.getEmail());
    AuthResponse response = authService.register(request);
    log.info("Registration successful for email='{}'", request.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "User registered successfully"));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
    log.info("Login attempt for email='{}'", request.getEmail());
    AuthResponse response = authService.login(request);
    log.info("Login successful for email='{}'", request.getEmail());
    return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
  }
}
