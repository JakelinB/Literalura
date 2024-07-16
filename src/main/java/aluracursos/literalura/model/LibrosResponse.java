package aluracursos.literalura.model;

public record LibrosResponse (
        Long libroId,
        String titulo,
        Idiomas idiomas,
        Double numeroDeDescargas,
        Long autorId,
        String nombreAutor
) {
    @Override
    public String toString() {

        return  "*********Datos del Libro**************" +
                "Titulo: " + titulo +
                "Nombre Autor='" + nombreAutor +
                "Idiomas=" + idiomas +
                "Numero De Descargas: " + numeroDeDescargas +
                "\n************************************\n";
    }
}
