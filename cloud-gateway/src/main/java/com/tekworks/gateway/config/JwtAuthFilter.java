package com.tekworks.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.tekworks.gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
    	
        return (exchange, chain) -> {
        	
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return handleUnauthorized(exchange);
            }

            String token = authHeader.substring(7); 

            try {
               
                String username = jwtUtil.extractUserName(token);
                if (username == null || jwtUtil.isTokenExpired(token)) {
                    return handleUnauthorized(exchange);
                }

              
                exchange.getRequest().mutate()
                        .header("X-User-Id", username)
                        .build();

            } catch (Exception e) {
                return handleUnauthorized(exchange);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
