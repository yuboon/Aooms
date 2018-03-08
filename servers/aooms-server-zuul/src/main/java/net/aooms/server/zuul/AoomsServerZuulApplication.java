package net.aooms.server.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.web.ZuulController;

@SpringBootApplication
@EnableZuulProxy
public class AoomsServerZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(AoomsServerZuulApplication.class, args);
	}
}
