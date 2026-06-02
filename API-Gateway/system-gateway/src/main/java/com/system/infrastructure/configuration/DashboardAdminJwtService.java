package com.system.infrastructure.configuration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class DashboardAdminJwtService {

    private final DashboardAuthProperties properties;

    public DashboardAdminJwtService(DashboardAuthProperties properties) {
        this.properties = properties;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + properties.getJwtExpirationMs());

        return Jwts.builder()
                .setSubject(username)
                .claim("role", "SUPER_ADMIN")
                .claim("source", "LOOCHON_DASHBOARD")
                .claim("authType", "super-admin")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(
                        SignatureAlgorithm.HS256,
                        properties.getJwtSecret().getBytes(StandardCharsets.UTF_8)
                )
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(properties.getJwtSecret().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}