package com.example.translator.services.login;

import com.example.translator.dto.login.request.LoginRequestDto;
import com.example.translator.dto.login.response.LoginResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.RoleEntity;
import com.example.translator.exceptions.LoginException;
import com.example.translator.exceptions.RoleNotFoundException;
import com.example.translator.repository.PersonRepository;
import com.example.translator.repository.RoleRepository;
import com.example.translator.services.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            PersonEntity personEntity = personRepository.findByEmail(loginRequestDto.getEmail());

            if (personEntity.isBlock()) {
                throw new LoginException("ACCOUNT_BLOCKED");
            }

            if (!personEntity.isVerify()) {
                throw new LoginException("ACCOUNT_UNVERIFIED");
            }

            RoleEntity roleEntity = roleRepository.findById(personEntity.getRole().getId())
                    .orElseThrow(() -> new RoleNotFoundException("Couldnt found role"));

            LoginResponseDto authResponseDto = new LoginResponseDto();
            authResponseDto.setToken(jwtService.generateToken(
                    userDetails,
                    personEntity.getId(),
                    personEntity.isBlock(),
                    personEntity.isActivate(),
                    personEntity.getEmail(),
                    personEntity.getGivenName(),
                    String.valueOf(roleEntity.getType()),
                    personEntity.isVerify()
            ));
            return authResponseDto;

        } catch (DisabledException e) {
            log.warn("Intento de login con cuenta desactivada: {}", loginRequestDto.getEmail());
            throw new LoginException("ACCOUNT_DELETED");
        } catch (LoginException e) {
            throw e;
        } catch (Exception e) {
            log.error("Tipo de excepción: {}", e.getClass().getName());
            log.error("Login failed: {}", e.getMessage(), e);
            throw new LoginException("It was impossible to do login");
        }
    }
}
