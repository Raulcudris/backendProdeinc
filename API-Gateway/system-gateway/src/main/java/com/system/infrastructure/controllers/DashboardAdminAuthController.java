package com.system.infrastructure.controllers;
import com.system.infrastructure.configuration.DashboardAdminJwtService;
import com.system.infrastructure.configuration.DashboardAuthProperties;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class DashboardAdminAuthController {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String AUTH_TYPE = "super-admin";
    private static final String USER_ROLE = "SUPER_ADMIN";
    private static final String USER_SOURCE = "LOOCHON_DASHBOARD";
    private static final String USER_NAME = "Super Administrador Loochon";

    private final DashboardAuthProperties properties;
    private final DashboardAdminJwtService dashboardAdminJwtService;

    public DashboardAdminAuthController(
            DashboardAuthProperties properties,
            DashboardAdminJwtService dashboardAdminJwtService
    ) {
        this.properties = properties;
        this.dashboardAdminJwtService = dashboardAdminJwtService;
    }

    @PostMapping("/super-login")
    public ResponseEntity<Map<String, Object>> superLogin(@RequestBody SuperLoginRequest request) {
        if (!properties.isSuperUserEnabled()) {
            return buildErrorResponse(HttpStatus.FORBIDDEN, "Login de super usuario deshabilitado");
        }

        if (isInvalidRequest(request)) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Usuario y contraseña son obligatorios");
        }

        if (!areValidCredentials(request)) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String username = request.getUsername().trim();
        String token = dashboardAdminJwtService.generateToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", TOKEN_TYPE);
        response.put("authType", AUTH_TYPE);
        response.put("user", buildUserResponse(username));

        return ResponseEntity.ok(response);
    }

    private boolean isInvalidRequest(SuperLoginRequest request) {
        return request == null
                || request.getUsername() == null
                || request.getUsername().trim().isEmpty()
                || request.getPassword() == null
                || request.getPassword().trim().isEmpty();
    }

    private boolean areValidCredentials(SuperLoginRequest request) {
        String username = request.getUsername().trim();
        String password = request.getPassword();

        return properties.getSuperUsername().equals(username)
                && properties.getSuperPassword().equals(password);
    }

    private Map<String, Object> buildUserResponse(String username) {
        Map<String, Object> user = new HashMap<>();

        user.put("username", username);
        user.put("name", USER_NAME);
        user.put("role", USER_ROLE);
        user.put("source", USER_SOURCE);

        return user;
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();

        error.put("message", message);
        error.put("status", status.value());

        return ResponseEntity.status(status).body(error);
    }

    public static class SuperLoginRequest {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}