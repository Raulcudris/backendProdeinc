package com.system.infrastructure.configuration;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class DashboardAdminAuthFilter {

    private static final Logger log = LoggerFactory.getLogger(DashboardAdminAuthFilter.class);

    private final DashboardAdminJwtService superAdminJwtService;

    public DashboardAdminAuthFilter(DashboardAdminJwtService superAdminJwtService) {
        this.superAdminJwtService = superAdminJwtService;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Token super admin no presente o mal formado");

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = superAdminJwtService.validateToken(token);

            String role = claims.get("role", String.class);
            String source = claims.get("source", String.class);
            String authType = claims.get("authType", String.class);

            if (!"SUPER_ADMIN".equals(role)
                    || !"LOOCHON_DASHBOARD".equals(source)
                    || !"super-admin".equals(authType)) {

                log.warn(
                        "Token super admin inválido. Role: {}, Source: {}, AuthType: {}",
                        role,
                        source,
                        authType
                );

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Auth-Source", "super-admin")
                    .header("X-Auth-Role", "SUPER_ADMIN")
                    .header("X-Auth-User", claims.getSubject())
                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(mutatedRequest)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error validando JWT super admin: {}", e.getMessage());

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}