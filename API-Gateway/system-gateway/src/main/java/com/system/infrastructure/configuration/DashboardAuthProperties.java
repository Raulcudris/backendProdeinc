package com.system.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "loochon.auth")
public class DashboardAuthProperties {

    private boolean superUserEnabled;
    private String superUsername;
    private String superPassword;
    private String jwtSecret;
    private Long jwtExpirationMs;

    public boolean isSuperUserEnabled() {
        return superUserEnabled;
    }

    public void setSuperUserEnabled(boolean superUserEnabled) {
        this.superUserEnabled = superUserEnabled;
    }

    public String getSuperUsername() {
        return superUsername;
    }

    public void setSuperUsername(String superUsername) {
        this.superUsername = superUsername;
    }

    public String getSuperPassword() {
        return superPassword;
    }

    public void setSuperPassword(String superPassword) {
        this.superPassword = superPassword;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public Long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(Long jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }
}