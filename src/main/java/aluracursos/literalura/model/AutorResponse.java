package aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record AutorResponse(
        Long libroId,
        String libros,
        Long autorId,
        String nombre,
        String fechaDeNacimiento,
        String fechaDeFallecimiento) {
    @Override
    public String toString() {
        return "\n*********Datos del Autor*************" +
                "\nnombre: " + nombre +
                "\nfechaDeNacimiento: " + fechaDeNacimiento +
                "\nfechaDeFallecimiento: " + fechaDeFallecimiento +
                "\nlibros='" + libros +
                "\n*****************************************\n";
    }
}
