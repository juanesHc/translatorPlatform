package com.example.translator.controller.login;

import com.example.translator.dto.login.request.LoginRequestDto;
import com.example.translator.dto.login.response.LoginResponseDto;
import com.example.translator.dto.messaging.request.SendVerificationRequestDto;
import com.example.translator.dto.messaging.response.SendVerificationResponseDto;
import com.example.translator.services.login.LoginService;
import com.example.translator.services.messaging.impl.MessagingService;
import com.example.translator.services.security.token.account.AccountRecoveryService;
import com.example.translator.services.security.token.email.VerifyEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;
    private final AccountRecoveryService accountRecoveryService;
    private final VerifyEmailService verifyEmailService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(loginService.login(loginRequestDto));
    }

    @PostMapping("/recover/resend")
    public ResponseEntity<String> resendRecovery(@RequestParam String email) {
        accountRecoveryService.sendRecoveryEmail(email);
        return ResponseEntity.ok("Email de recuperación reenviado");
    }

    @GetMapping("/recover")
    public ResponseEntity<String> recoverAccount(@RequestParam String token) {
        accountRecoveryService.recoverAccount(token);
        return ResponseEntity.ok("Cuenta recuperada exitosamente");
    }

    @GetMapping("/verify")
    public ResponseEntity<SendVerificationResponseDto> verifyEmail(@RequestParam String token) {
        verifyEmailService.verifyEmail(token);
        return ResponseEntity.ok(new SendVerificationResponseDto("Correo verificado exitosamente"));
    }

    @PostMapping("/verify/resend")
    public ResponseEntity<SendVerificationResponseDto> resendVerifyEmail(@RequestParam String email) {
        verifyEmailService.reVerifyEmail(email);
        return ResponseEntity.ok(new SendVerificationResponseDto("Correo verificado exitosamente"));
    }


}
