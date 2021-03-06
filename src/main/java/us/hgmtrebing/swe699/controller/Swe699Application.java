package us.hgmtrebing.swe699.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import us.hgmtrebing.swe699.database.MysqlConnection;

import java.util.Arrays;

@SpringBootApplication
public class Swe699Application {

	public static void main(String[] args) {
		new RestaurantManagerRequestor();
		SpringApplication.run(Swe699Application.class, args);
	}

	/*
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	 */

}
