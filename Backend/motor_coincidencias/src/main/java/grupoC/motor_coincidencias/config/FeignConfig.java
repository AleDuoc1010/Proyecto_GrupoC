package grupoC.motor_coincidencias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import grupoC.motor_coincidencias.security.JwtTokenProvider;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignAuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        return template -> {
            try {
                String token = jwtTokenProvider.generateServiceToken("motor-service");
                System.out.println("DEBUG: FeignConfig attaching token prefix: " + (token != null && token.length() > 8 ? token.substring(0,8) : token));
                template.header("Authorization", "Bearer " + token);
            } catch (Exception ex) {
                // ignore
            }
        };
    }

}
