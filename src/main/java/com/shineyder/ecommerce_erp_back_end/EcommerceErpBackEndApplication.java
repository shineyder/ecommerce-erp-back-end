package com.shineyder.ecommerce_erp_back_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EcommerceErpBackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceErpBackEndApplication.class, args);
	}
}
