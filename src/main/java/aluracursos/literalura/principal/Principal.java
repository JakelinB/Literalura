package aluracursos.literalura.principal;

import aluracursos.literalura.model.*;
import aluracursos.literalura.repositorio.AutorRepository;
import aluracursos.literalura.repositorio.LibroRepository;
import aluracursos.literalura.service.ConsumirAPI;
import aluracursos.literalura.service.ConvierteDatos;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;


    public Principal(LibroRepository repository, AutorRepository repositoryA) {
        this.libroRepository = repository; //Creamos el constructor, para poder obtener la inyección de la dependencia y poder intancias objetos del tipo SerieRepository
        this.autorRepository = repositoryA;
    }
    public void muestraElMenu(){
        var opcion = -1;

        while (opcion!= 9) {
            var menu = """
                    *** LiterAlura Libros ***                    
                                        
                    1- Buscar Libro por titulo
                    2- Mostrar Libros Guardados
                    3- Mostrar autores registrados
                    4- Buscar autores vivos en año determinado
                    5- Buscar Libros por idioma
                                    
                    9 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSLibroWeb();
                    break;
                case 2:
                    mostrarLibrosGuardados();
                    break;
                case 3:
                    mostrarAutoresGuardados();
                    break;
                case 4:
                    mostrarAutorPorAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 9:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibros getDatos(){
        Gson gson = new Gson();
        System.out.println("Escriba el titulo del libro que deseas buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));

        var datosBusquedad = conversor.obtenerDatos(json, Datos.class); //Extraemos el results que trae la consulta

        Optional<DatosLibros> libroBuscado = datosBusquedad.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado!! ");
            System.out.println("Los datos Son: "+ libroBuscado.get());
            return libroBuscado.get();
        } else
            return null;
    }

    private void buscarSLibroWeb() {
        DatosLibros datos = getDatos();
        Libros libro = new Libros(datos);
        try {
        List<Autor> autores = datos.autor().stream()
                .map(datosAutor -> new Autor(datosAutor))
                .collect(Collectors.toList());
        libro.setAutor(autores);
        autores.forEach(autor -> autor.getLibros().add(libro));

        libroRepository.save(libro);

        }catch (Exception e){
            System.out.println("Lo siento, no se pueden agregar libros repetidos!!!");
        }

    }

    private void mostrarLibrosGuardados(){
        List<LibrosResponse> librosConAutores = libroRepository.findAllLibrosAndAutorNombre();

        for (LibrosResponse datos : librosConAutores) {
            System.out.println( "\nLibro: " + datos.titulo() +
                                "\nAutor: " + datos.nombreAutor() +
                                "\nIdiomas: " + datos.idiomas() +
                                "\nNumero de Descargas: " + datos.numeroDeDescargas() + "\n"
                    );
        }

    }

    private void mostrarAutoresGuardados(){
        List<AutorResponse> AutoresConLibros = libroRepository.findAllAutorAndLibrosNombre();

        for (AutorResponse datos : AutoresConLibros) {
            System.out.println( "\nNombre: " + datos.nombre() +
                    "\nFecha de Nacimiento: " + datos.fechaDeNacimiento() +
                    "\nFecha de fallecimiento: " + datos.fechaDeFallecimiento() +
                    "\nLibros " + datos.libros() +"\n"
            );
    }
    }

    private void mostrarAutorPorAño() {

        System.out.println("Escriba el año del que desea buscar el autor: ");
        var fechaInicial = teclado.nextLine();

        List<Autor> autores = autorRepository.findAutoresByFechaDeFallecimientoBefore(fechaInicial);
        if(!autores.isEmpty()){
            System.out.println("No se encontraron autores vivos en dicho año");
        }else{
        autores.forEach(System.out::println);}

//filtrar autores vivos en determinado año, desde la API
/*var json = consumirAPI.obtenerDatos(URL_BASE + "?author_year_end=" + fechaInicial);
        var datosBusquedadPorFecha = conversor.obtenerDatos(json, Datos.class);

        List<DatosAutor> autoresFiltrados = datosBusquedadPorFecha.resultados().stream()
                .flatMap(libro -> libro.autor().stream())
                .filter(autor -> {
                    try {
                        int añoMuerte = Integer.parseInt(autor.fechaDeFallecimiento());
                        return añoMuerte < fechaInicial;
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el año de muerte: " + autor.fechaDeFallecimiento());
                        return false; // O manejar de otra manera, por ejemplo, excluir autores con datos incorrectos
                    }
                })
                .collect(Collectors.toList());

        // Mostrar los autores filtrados
        autoresFiltrados.forEach(autor -> System.out.println("Autor: " + autor.nombre() + ", Año de muerte: " + autor.fechaDeFallecimiento()));

    }*/
    }
    private void listarLibrosPorIdioma() {
        System.out.println("""
                Menú de opciones:
                1. Inglés
                2. Alemán
                3. Español
                4. Italiano
                5. Rusia
                6. Chino
                7. Portugués
                \nRecuerde, debe escribir el numero de la opción.
                """);
        System.out.print("Selecciona el número del idioma de los libros que deseas obtener: ");
        var idiomaSeleccionado = teclado.nextInt();
        if (idiomaSeleccionado > 7 || idiomaSeleccionado < 1) {
            System.out.println("Opción inválida, seleccione una opción del menú.");
        }

        Idiomas opcionIdioma = null;

        switch (idiomaSeleccionado) {
            case 1:
                opcionIdioma = Idiomas.ENGLISH;
                break;
            case 2:
                opcionIdioma = Idiomas.GERMAN;
                break;
            case 3:
                opcionIdioma = Idiomas.SPANISH;
                break;
            case 4:
                opcionIdioma = Idiomas.ITALIAN;
                break;
            case 5:
                opcionIdioma = Idiomas.RUSSIAN;
                break;
            case 6:
                opcionIdioma = Idiomas.CHINESE;
                break;
            case 7:
                opcionIdioma = Idiomas.PORTUGUES;
                break;
            default:
                System.out.println("Error, no se encontrarón libros con ese idioma ");
        }


        List<LibrosResponse> libroPorIdioma = libroRepository.findLibrosAndAutoresByIdioma(opcionIdioma);

        if (!libroPorIdioma.isEmpty()) {
            for (LibrosResponse datos : libroPorIdioma) {
                libroPorIdioma.forEach(System.out::println);
            }
        } else{
            System.out.println("No hay libros registrados en ese idioma. ");
        }
        }
    }


