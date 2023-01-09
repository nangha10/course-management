//package com.example.courseservice.feignclients;
//
//import feign.Feign;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//@LoadBalancerClient(value="teacher-service")
//
//public class TeaSerLoadBalConfig {
//    @LoadBalanced
//    @Bean
//    public Feign.Builder feignBuilder () {
//        return Feign.builder();
//    }
//}
