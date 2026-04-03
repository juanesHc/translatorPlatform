package com.example.translator.controller.login;

import com.example.translator.dto.login.request.LoginRequestDto;
import com.example.translator.dto.login.response.LoginResponseDto;
import com.example.translator.services.login.LoginService;
import com.example.translator.services.security.token.account.AccountRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;
    private final AccountRecoveryService accountRecoveryService;

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

}
