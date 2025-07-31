package org.example.bankcards.security;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Класс {@code SecurityConfig} реализует конфигурацию безопасности приложения.
 * Настраивает цепочку фильтров безопасности, аутентификацию, авторизацию, CORS и шифрование паролей.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Метод настройки цепочки фильтров безопасности.
     * <p>
     * Определяет, какие запросы требуют аутентификации, какая политика сессий применяется
     * и какие фильтры добавляются к цепочке обработки запроса.
     *
     * @param http объект HttpSecurity, с помощью которого настраиваются параметры безопасности
     * @return настроенная цепочка безопасности (SecurityFilterChain)
     * @throws Exception если произошла ошибка при настройке
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/swagger-resources/*", "/v3/**").permitAll()
                        .requestMatchers("/api/admin/**", "api/cards/**").hasRole("ADMIN")
                        .requestMatchers("/api/cards/**").hasAnyRole("USER")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Метод, создающий бин кодировщика паролей.
     * <p>
     * Используется BCryptPasswordEncoder для хэширования паролей.
     *
     * @return объект PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Метод, создающий бин провайдера аутентификации.
     * <p>
     * Используется DaoAuthenticationProvider, который использует UserDetailsService
     * и PasswordEncoder для проверки учетных данных.
     *
     * @return объект AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Метод, создающий бин менеджера аутентификации.
     * <p>
     * Используется для выполнения процесса аутентификации в Spring Security.
     *
     * @param config конфигурация аутентификации
     * @return объект AuthenticationManager
     * @throws Exception если произошла ошибка при создании менеджера
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Метод настройки CORS (Cross-Origin Resource Sharing).
     * <p>
     * Разрешает запросы только с определенного домена и указывает разрешенные методы и заголовки.
     *
     * @param registry объект CorsRegistry, с помощью которого настраиваются параметры CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
    }
}
