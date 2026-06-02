package com.system.infrastructure.configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, ConditionalAuthFilter conditionalAuthFilter) {
        return builder.routes()
                .route("conditional_route", r -> r.path("/secure/**")
                        .filters(f -> f.filter(conditionalAuthFilter.apply(new ConditionalAuthFilter.Config())))
                        .uri("lb://YOUR-SERVICE"))
                .build();
    }
}
