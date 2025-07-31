package org.example.bankcards.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;

/**
 * Компонент, отвечающий за экспорт OpenAPI спецификации в JSON-файл.
 */
@Slf4j
@Component
public class OpenApiExporter implements ApplicationRunner {

    /**
     * Метод, вызываемый при запуске приложения.
     * Выполняет запрос к локальному серверу для получения OpenAPI документации
     * и сохраняет её в файл.
     */
    @Override
    public void run(ApplicationArguments args) {
        String url = "http://localhost:8080/v3/api-docs";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                try (FileWriter writer = new FileWriter("docs/openapi.json")) {
                    writer.write(response.getBody());
                    log.info("openapi.json успешно сохранён");
                }
            }
        } catch (Exception e) {
            log.error("Ошибка при получении openapi.json: {}", e.getMessage());
        }
    }
}
