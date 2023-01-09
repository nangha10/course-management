//package com.example.courseservice.feignclients;
//
//import feign.Feign;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//
//@LoadBalancerClient(value="student-service")
//public class StuSerLoadBalConfig {
//    @LoadBalanced
//    @Bean
//    public Feign.Builder feignBuilder () {
//        return Feign.builder();
//    }
//}
