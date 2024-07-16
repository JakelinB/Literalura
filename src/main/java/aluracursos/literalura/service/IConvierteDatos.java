package aluracursos.literalura.service;

public interface IConvierteDatos {
    <T> T /*Este tipo de declaraciÃ³n se hace cuando necesitamos indicar que son datos genericos
    Puesto que no sabemos que tipo de datos podremos tratar, o sea, que sea una cosa u otra
    */ obtenerDatos(String json, Class<T> clase);
}
