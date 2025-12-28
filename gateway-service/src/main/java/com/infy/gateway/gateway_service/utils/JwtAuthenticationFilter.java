package com.infy.gateway.gateway_service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
@Component
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String authHeader =
                    exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = jwtUtil.validateToken(token);

                exchange.getRequest()
                        .mutate()
                        .header("X-User-Id", claims.getSubject())
                        .header("X-User-Role", claims.get("role", String.class))
                        .build();

            } catch (Exception e) {
                return unauthorized(exchange);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}
