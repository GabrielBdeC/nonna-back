package br.com.nonna_back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Roda uma vez em TODA requisição, antes de qualquer Controller.
 * Só tenta ler o token e, se for válido, anota quem é o usuário em
 * UsuarioAutenticado -- não bloqueia nada aqui. Quem decide se a rota
 * exige login (e qual papel) é o próprio Controller, chamando os métodos
 * de Autorizacao. Separar "quem está pedindo" de "o que essa rota exige"
 * deixa as duas coisas mais fáceis de explicar separadamente.
 */
@Component
public class AutenticacaoFiltro extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public AutenticacaoFiltro(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String cabecalho = request.getHeader("Authorization");

        if (cabecalho != null && cabecalho.startsWith("Bearer ")) {
            String token = cabecalho.substring("Bearer ".length());
            try {
                Claims claims = jwtUtil.validarEExtrairClaims(token);
                UsuarioAutenticado.autenticar(claims.getSubject(), claims.get("tipo", String.class));
            } catch (JwtException | IllegalArgumentException ignorado) {
                // Token ausente, expirado ou adulterado: segue a requisição como anônima.
                // A rota protegida é quem vai recusar, via Autorizacao.exigirAutenticado().
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            // Essencial: o Tomcat reaproveita threads entre requisições.
            // Sem isso, o próximo request na mesma thread "herdaria" este usuário.
            UsuarioAutenticado.limpar();
        }
    }
}
