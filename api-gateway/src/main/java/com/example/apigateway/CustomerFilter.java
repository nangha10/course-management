//package com.example.apigateway;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class CustomerFilter implements GlobalFilter {
//    Logger logger = LoggerFactory.getLogger(CustomerFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
//        logger.info("Authorization = " + request.getHeaders().getFirst("Authorization"));
//        return chain.filter(exchange);
//    }
//}
