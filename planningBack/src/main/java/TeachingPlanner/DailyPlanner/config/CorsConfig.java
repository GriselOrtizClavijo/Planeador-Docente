package TeachingPlanner.DailyPlanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // Opción 1: Permitir ambos puertos (más seguro)
                        .allowedOrigins("http://localhost:5173", "http://localhost:5174")

                        // Opción 2: Para desarrollo, permite cualquier puerto local (menos estricto)
                        // .allowedOrigins("http://localhost:*")

                        .allowedMethods("*") // Permitir todos los métodos
                        .allowedHeaders("*");
            }
        };
    }
}