package org.example.bankcards.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр аутентификации по JWT-токену.
 * <p>
 * Этот фильтр проверяет каждый HTTP-запрос на наличие валидного JWT-токена в заголовке "Authorization".
 * Если токен валиден, создается объект аутентификации и устанавливается в контексте безопасности Spring.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Префикс, указывающий, что токен является Bearer-токеном.
     * Пример: "Bearer abcdef123456"
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Имя заголовка HTTP, в котором передаётся JWT-токен.
     * Обычно: "Authorization"
     */
    public static final String HEADER_NAME = "Authorization";

    /**
     * Сервис для работы с JWT-токенами.
     * Отвечает за извлечение данных из токена и его валидацию.
     */
    private final JwtService jwtService;

    /**
     * Сервис для загрузки информации о пользователе по имени.
     * Используется Spring Security для получения деталей пользователя.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Метод, вызываемый при обработке каждого HTTP-запроса.
     * Проверяет наличие и валидность JWT-токена в заголовке запроса.
     *
     * @param request  объект HttpServletRequest, содержащий входящий HTTP-запрос
     * @param response объект HttpServletResponse, содержащий ответ сервера
     * @param filterChain цепочка фильтров Spring Security
     * @throws ServletException если произошла ошибка в процессе обработки запроса
     * @throws IOException      если произошла ошибка ввода-вывода
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var username = jwtService.extractUserName(jwt);

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
