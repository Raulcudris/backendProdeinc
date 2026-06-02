package com.system.infrastructure.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import reactor.core.publisher.Mono;

@Component
public class FirebaseAuthFilter extends AbstractGatewayFilterFactory<FirebaseAuthFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthFilter.class);

    public FirebaseAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("✅ Iniciando verificacion token FirebaseAuthFilter");

            ServerHttpRequest request = exchange.getRequest();

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Encabezado Authorization no presente o mal formado");
                return Mono.error(new RuntimeException("Falta el token de autenticación"));
            }

            String token = authHeader.substring(7); // Quitar 'Bearer '
            log.info("✅ Token FirebaseAuthFilter para verificar: {}"+token);

            return Mono.fromCallable(() -> {
                        // Validación con Firebase
                        log.info("✅ Iniciando verificación...:");
                        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                        String uid = decodedToken.getUid();
                        log.info("✅ Usuario autenticado correctamente. UID: {}", uid);
                        return exchange;
                    })
                    .flatMap(chain::filter)
                    .onErrorResume(e -> {
                        log.error("❌ Error al validar token JWT: {}", e.getMessage());
                        return Mono.error(new RuntimeException("Token JWT inválido: " + e.getMessage()));
                    });
        };
    }

    public static class Config {
        // Puedes extender esta clase si necesitas configuración adicional
    }
}
