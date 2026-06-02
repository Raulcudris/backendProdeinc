package com.system.infrastructure.configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MySQLAuthFilter implements GatewayFilter {

    private static final Logger log = LoggerFactory.getLogger(MySQLAuthFilter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            log.warn("[MySQLAuthFilter] Falta Authorization. Path: {}", path);
            return unauthorizedResponse(exchange, "Falta el encabezado Authorization");
        }

        String token = cleanBearerToken(authorizationHeader);

        String[] tokenParts = token.split("\\.");
        if (tokenParts.length != 3) {
            log.warn(
                    "[MySQLAuthFilter] Formato JWT inválido. Path: {}, TokenPreview: {}",
                    path,
                    maskToken(token)
            );

            return unauthorizedResponse(exchange, "Formato de token inválido");
        }

        try {
            String payload = decodeJwtPayload(tokenParts[1]);

            String iduser = extractRequiredValueFromPayload(payload, "user_id");
            long iat = Long.parseLong(extractRequiredValueFromPayload(payload, "iat"));
            long exp = Long.parseLong(extractRequiredValueFromPayload(payload, "exp"));

            String url = "http://msvc-usersaccess/api/useraccess/validatoken?iduser={iduser}&iat={iat}&exp={exp}";

            log.info(
                    "[MySQLAuthFilter] Validando token MySQL. Path: {}, user_id: {}, iat: {}, exp: {}",
                    path,
                    iduser,
                    iat,
                    exp
            );

            return webClientBuilder.build()
                    .get()
                    .uri(url, iduser, iat, exp)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        if ("true".equalsIgnoreCase(response)) {
                            log.info(
                                    "[MySQLAuthFilter] Token validado correctamente. Path: {}, user_id: {}",
                                    path,
                                    iduser
                            );

                            return chain.filter(exchange);
                        }

                        log.warn(
                                "[MySQLAuthFilter] Token rechazado por msvc-usersaccess. Path: {}, user_id: {}",
                                path,
                                iduser
                        );

                        return unauthorizedResponse(exchange, "Token no es válido");
                    })
                    .onErrorResume(error -> {
                        log.error(
                                "[MySQLAuthFilter] Error consultando msvc-usersaccess. Path: {}, Error: {}",
                                path,
                                error.getMessage()
                        );

                        return unauthorizedResponse(exchange, "Error al validar el token con el servicio de autenticación");
                    });

        } catch (IllegalArgumentException e) {
            log.warn(
                    "[MySQLAuthFilter] Token inválido o payload incompleto. Path: {}, Error: {}, TokenPreview: {}",
                    path,
                    e.getMessage(),
                    maskToken(token)
            );

            return unauthorizedResponse(exchange, "Token inválido o payload incompleto");

        } catch (Exception e) {
            log.error(
                    "[MySQLAuthFilter] Error inesperado procesando token. Path: {}, Error: {}",
                    path,
                    e.getMessage()
            );

            return unauthorizedResponse(exchange, "Error al procesar el token");
        }
    }

    private String cleanBearerToken(String authorizationHeader) {
        String token = authorizationHeader.trim();

        if (token.toLowerCase().startsWith("bearer ")) {
            return token.substring(7).trim();
        }

        return token;
    }

    private String decodeJwtPayload(String encodedPayload) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedPayload);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo decodificar el payload del token JWT");
        }
    }

    private String extractRequiredValueFromPayload(String payload, String key) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> json = objectMapper.readValue(payload, Map.class);

            Object value = json.get(key);

            if (value == null) {
                throw new IllegalArgumentException("No se encontró el campo requerido '" + key + "' en el payload del token");
            }

            return value.toString();

        } catch (IllegalArgumentException e) {
            throw e;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error leyendo el campo '" + key + "' del payload del token");
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        String path = exchange.getRequest().getURI().getPath();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", message);
        body.put("path", path);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));

        } catch (Exception e) {
            String fallbackBody = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"" + message + "\",\"path\":\"" + path + "\"}";
            DataBuffer buffer = response.bufferFactory().wrap(fallbackBody.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
    }

    private String maskToken(String token) {
        if (token == null || token.length() < 20) {
            return "TOKEN_OCULTO";
        }
        return token.substring(0, 10) + "..." + token.substring(token.length() - 6);
    }
}