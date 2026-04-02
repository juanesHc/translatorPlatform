package com.example.translator.entity;

import com.example.translator.entity.enums.TokenTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="tokens")
public class TokenEntity extends BaseEntity{

    @Column(unique=true)
    private String token;

    @Column(updatable = false , nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PersonEntity person;

    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

}
