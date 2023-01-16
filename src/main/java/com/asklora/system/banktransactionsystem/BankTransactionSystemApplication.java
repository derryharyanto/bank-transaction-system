package com.asklora.system.banktransactionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan({"com.asklora.system.bankmodelorm","com.asklora.system.banktransactionsystem"})
@SpringBootApplication
@EnableJpaAuditing
public class BankTransactionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransactionSystemApplication.class, args);
	}

}
