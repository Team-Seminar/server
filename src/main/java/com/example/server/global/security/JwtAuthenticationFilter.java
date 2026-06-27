package com.example.server.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 요청 헤더에서 JWT 토큰을 추출합니다.
        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                // 1. 복호화를 시도합니다. (내부적으로 검증이 같이 일어남!)
                Map<String,Object> map = tokenManager.getToken(token);

                // 2. 복호화에 성공했다는 건 안전하다는 뜻이니 바로 권한을 추출합니다.

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(id, null, List.of(authority));

                // 3. 시큐리티 보관함에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // 토큰이 만료되었거나, 위조되었거나, 복호화 중 에러가 나면 이 감옥으로 떨어집니다.
                // 아무런 처리를 하지 않으면 시큐리티 보관함이 비어있게 되므로, 자연스럽게 컨트롤러 입구에서 튕겨 나갑니다.
                logger.error("JWT 토큰 검증 실패: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청 헤더에서 "Authorization: Bearer [토큰]" 형태의 문자열을 찾아
     * 순수한 토큰(알맹이)만 쏙 빼오는 메서드입니다.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 뒷부분의 토큰만 반환
        }
        return null;
    }
}
