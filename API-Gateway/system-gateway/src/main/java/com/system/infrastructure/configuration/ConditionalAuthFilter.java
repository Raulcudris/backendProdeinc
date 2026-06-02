package com.system.infrastructure.configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ConditionalAuthFilter extends AbstractGatewayFilterFactory<ConditionalAuthFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(ConditionalAuthFilter.class);

    private final FirebaseAuthFilter firebaseAuthFilter;
    private final MySQLAuthFilter mysqlAuthFilter;
    private final DashboardAdminAuthFilter superAdminAuthFilter;

    public ConditionalAuthFilter(
            FirebaseAuthFilter firebaseAuthFilter,
            MySQLAuthFilter mysqlAuthFilter,
            DashboardAdminAuthFilter superAdminAuthFilter
    ) {
        super(Config.class);
        this.firebaseAuthFilter = firebaseAuthFilter;
        this.mysqlAuthFilter = mysqlAuthFilter;
        this.superAdminAuthFilter = superAdminAuthFilter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            String authType = exchange.getRequest().getHeaders().getFirst("Auth-Type");
            HttpMethod method = exchange.getRequest().getMethod();

            if (HttpMethod.OPTIONS.equals(method)) {
                log.info("[ConditionalAuthFilter] Preflight OPTIONS permitido. Path: {}", path);
                return chain.filter(exchange);
            }

            if (isPublicPath(path)) {
                log.info("[ConditionalAuthFilter] Ruta pública permitida. Path: {}", path);
                return chain.filter(exchange);
            }

            if (authType == null || authType.trim().isEmpty() || "mysql".equalsIgnoreCase(authType)) {
                log.info("[ConditionalAuthFilter] Autenticación MySQL. Path: {}", path);
                return mysqlAuthFilter.filter(exchange, chain);
            }

            if ("firebase".equalsIgnoreCase(authType)) {
                log.info("[ConditionalAuthFilter] Autenticación Firebase. Path: {}", path);
                return firebaseAuthFilter
                        .apply(new FirebaseAuthFilter.Config())
                        .filter(exchange, chain);
            }

            if ("super-admin".equalsIgnoreCase(authType)) {
                log.info("[ConditionalAuthFilter] Autenticación Super Admin. Path: {}", path);
                return superAdminAuthFilter.filter(exchange, chain);
            }

            log.warn(
                    "[ConditionalAuthFilter] Auth-Type no soportado. Path: {}, Auth-Type: {}",
                    path,
                    authType
            );

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }

    private boolean isPublicPath(String path) {
        return "/api/auth/super-login".equals(path)
                || "/api/health".equals(path);
    }

    public static class Config {
        // Configuración adicional si es necesaria
    }
}