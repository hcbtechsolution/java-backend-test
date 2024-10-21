package com.hcbtechsolutions.parkinglotsmanagement.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("establishment-service",
						r -> r.path("/establishments/**")
								.uri("lb://ESTABLISHMENT-SERVICE"))
				.route("vehicle-service",
						r -> r.path("/vehicles/**")
								.uri("lb://VEHICLE-SERVICE"))
				.route("parking-control-service",
						r -> r.path("/parking-control/**")
								.uri("lb://PARKING-CONTROL-SERVICE"))
				.build();
	}
}
