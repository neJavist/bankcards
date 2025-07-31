package org.example.bankcards.security.service;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения информации о пользователе по его имени.
 * Используется Spring Security для аутентификации и авторизации.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Метод загрузки пользователя по его имени.
     * <p>
     * Выполняет поиск пользователя в базе данных. Если пользователь не найден,
     * выбрасывается исключение {@link UsernameNotFoundException}.
     *
     * @param username имя пользователя, которое необходимо найти
     * @return объект {@link UserDetails}, представляющий пользователя
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByName(username)
                .orElseThrow(UserNotFoundException::getUserNotFoundException);
    }
}
