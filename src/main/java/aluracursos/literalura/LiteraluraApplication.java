package aluracursos.literalura;

import aluracursos.literalura.principal.Principal;
import aluracursos.literalura.repositorio.AutorRepository;
import aluracursos.literalura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
@Autowired
private LibroRepository repository;
@Autowired
private AutorRepository repositoryA;
	public static void main(String[] args) {

		SpringApplication.run(LiteraluraApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, repositoryA); //aqui lo paso a donde queremos hacer la utiización, en este caso a principal, donde debo de crear un constructor
		principal.muestraElMenu();
	}

}
