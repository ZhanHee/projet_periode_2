package Serdaigle.MIAGIE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration de CORS pour autoriser les requêtes depuis n'importe quelle origine
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * NE JAMAIS FAIRE CA EN PROD (mais ici nous n'avons pas le temps donc pour du local on dira que ça passe)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // <-- Remplace "/*" par "/**"
                .allowedOrigins("http://localhost:5173")  // Retire le slash à la fin de l'URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*");
    }
}
