package com.example.translator.services.security.jwt;

import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.security.SecurityUser;
import com.example.translator.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        PersonEntity personEntity=personRepository.findByEmail(email);
        if(personEntity==null){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new SecurityUser(personEntity);
    }
}
