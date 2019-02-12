package ru.girchev.springbootjpaexamples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import java.io.Console;

@SpringBootApplication
public class SpringBootJpaExamplesApplication
//		implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaExamplesApplication.class, args);
	}

//	/**
//	 * Horrible piece of shit
//	 * To have time to work @PostInitialize
//	 * @param args
//	 */
//	@Override
//	public void run(String... args) {}


//	@Bean
//	public LocalEntityManagerFactoryBean entityManagerFactory(){
//		LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
//		factoryBean.setPersistenceUnitName("cpJpaPu");
//		return factoryBean;
//	}
}

