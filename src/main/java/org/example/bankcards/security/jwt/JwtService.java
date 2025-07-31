package org.example.bankcards.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.bankcards.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервис для работы с JWT-токенами.
 * <p>
 * Реализует функции генерации, извлечения данных и проверки валидности токенов.
 */
@Service
public class JwtService {
    /**
     * Секретный ключ для подписи токенов.
     * <p>
     * Значение загружается из файла конфигурации (application.properties).
     */
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * Извлекает имя пользователя из JWT-токена.
     *
     * @param token JWT-токен
     * @return имя пользователя, указанное в токене
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерирует новый JWT-токен на основе информации о пользователе.
     *
     * @param userDetails информация о пользователе
     * @return сгенерированный JWT-токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("role", customUserDetails.getRole());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Проверяет валидность JWT-токена.
     * <p>
     * Токен считается валидным, если он не истёк и имя пользователя из токена совпадает
     * с именем пользователя из объекта UserDetails.
     *
     * @param token       JWT-токен
     * @param userDetails информация о пользователе
     * @return true, если токен валиден, иначе false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлекает значение из JWT-токена с помощью переданной функции.
     *
     * @param token             JWT-токен
     * @param claimsResolvers   функция для извлечения значения из Claims
     * @param <T>               тип возвращаемого значения
     * @return извлеченное значение
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерирует JWT-токен на основе дополнительных утверждений и информации о пользователе.
     *
     * @param extraClaims     дополнительные утверждения (например, id, email, роль)
     * @param userDetails     информация о пользователе
     * @return сгенерированный JWT-токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Проверяет, истёк ли срок действия JWT-токена.
     *
     * @param token JWT-токен
     * @return true, если токен истёк, иначе false
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлекает дату истечения срока действия JWT-токена.
     *
     * @param token JWT-токен
     * @return дата истечения срока действия
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает все утверждения (claims) из JWT-токена.
     *
     * @param token JWT-токен
     * @return объект Claims, содержащий информацию из токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получает секретный ключ для подписи JWT-токенов.
     *
     * @return объект Key, представляющий секретный ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
