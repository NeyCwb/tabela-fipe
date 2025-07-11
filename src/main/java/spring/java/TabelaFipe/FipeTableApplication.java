package spring.java.TabelaFipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring.java.TabelaFipe.principal.Principal;

@SpringBootApplication

public class FipeTableApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FipeTableApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		
		principal.exibeMenu();
	}

}
