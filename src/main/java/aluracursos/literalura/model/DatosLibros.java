package aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros (
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDescargas
){
    @Override
    public String toString() {
        return  "\n********Datos Del Libro*********" +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor +
                "\nIdiomas: " + idiomas +
                "\nNumero de Descargas: " + numeroDeDescargas +
                "\n************************************\n";
    }
}
