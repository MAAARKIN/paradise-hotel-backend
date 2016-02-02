package br.com.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.backend.model.User;
import br.com.backend.repository.UserRepository;

@Component
public class InitComponent implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(InitComponent.class);
	@Autowired private UserRepository repository;
	
	public void run(String... args) {
		
		System.out.println("rodou");
		System.out.println(repository);
		repository.save(new User("Jack", "12345"));
		repository.save(new User("Chloe", "54321"));
		repository.save(new User("Kim", "teste"));
		repository.save(new User("David", "teste2"));
		repository.save(new User("Michelle", "teste3"));

		// fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		for (User user : repository.findAll()) {
			log.info(user.toString());
		}
		log.info("");

		// fetch an individual customer by ID
		User user = repository.findOne(1L);
		log.info("Customer found with findOne(1L):");
		log.info("--------------------------------");
		log.info(user.toString());
		log.info("");

		// fetch customers by last name
		log.info("Customer found with findByLastName('Jack'):");
		log.info("--------------------------------------------");
		for (User bauer : repository.findByName("Jack")) {
			log.info(bauer.toString());
		}
		log.info("");
    }
}
