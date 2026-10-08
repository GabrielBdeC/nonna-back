package br.com.nonna_back.security;

import br.com.nonna_back.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Gera e lê o JWT. Um JWT é só um JSON (quem é o usuário, quando expira)
 * assinado com uma chave secreta -- qualquer um pode LER o conteúdo
 * (não é criptografado), mas só quem tem a chave consegue criar um token
 * com assinatura válida. É por isso que validar = conferir a assinatura.
 */
@Component
public class JwtUtil {
    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtUtil(@Value("${jwt.secret}") String segredo, @Value("${jwt.expiracao-ms}") long expiracaoMs) {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMs = expiracaoMs;
    }

    public String gerarToken(Usuario usuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiracaoMs);

        return Jwts.builder()
                .subject(usuario.getId())
                .claim("tipo", usuario.getTipo())
                .claim("nome", usuario.getNome())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chave)
                .compact();
    }

    public Claims validarEExtrairClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
