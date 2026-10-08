package br.com.nonna_back.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SegurancaConfig {

    /**
     * BCrypt gera um "sal" aleatório diferente a cada chamada, por isso o
     * mesmo "123456" nunca produz o mesmo hash duas vezes -- e ainda assim
     * matches() consegue confirmar que é a senha certa. Isso dificulta
     * ataques de rainbow table (hash pré-calculado para senhas comuns).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
