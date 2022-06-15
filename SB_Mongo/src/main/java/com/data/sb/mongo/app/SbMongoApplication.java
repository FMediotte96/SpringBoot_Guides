package com.data.sb.mongo.app;

import com.data.sb.mongo.app.entities.Customer;
import com.data.sb.mongo.app.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbMongoApplication implements CommandLineRunner {

	private final CustomerRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(SbMongoApplication.class);

	@Autowired
	public SbMongoApplication(CustomerRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SbMongoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		//save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));

		//fetch all customers
		LOGGER.info("Customers found with findAll():");
		for (Customer customer : repository.findAll()) {
			LOGGER.info("Customer: {}", customer);
		}

		//Fetch an individual customer
		LOGGER.info("Customer found with findByFirstName('Alice'):");
		Customer alice = repository.findByFirstName("Alice");
		LOGGER.info("Customer: {}", alice);

		LOGGER.info("Customers found with findByLastName('Smith'):");
		for (Customer customer : repository.findByLastName("Smith")) {
			LOGGER.info("Customer: {}", customer);
		}
	}
}
