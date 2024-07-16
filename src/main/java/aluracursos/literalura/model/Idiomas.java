package aluracursos.literalura.model;

public enum Idiomas {
    ENGLISH("en", "inglés"),
    GERMAN("de", "alemán"),
    FRENCH("fr", "francés"),
    SPANISH("es", "español"),
    ITALIAN("it", "italiano"),
    RUSSIAN("ru", "ruso"),
    CHINESE("zh", "chino"),
    PORTUGUES("pt", "portugués");

    private String codigoIdioma;
    private String nombreIdioma;

    Idiomas (String codigoIdioma, String languageSpanish){
        this.codigoIdioma = codigoIdioma;
        this.nombreIdioma = languageSpanish;
    }

    public static Idiomas getNombrePorCodigo(String code) {
        for (Idiomas idioma: Idiomas.values()){
            if (idioma.codigoIdioma.equalsIgnoreCase(code)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró opción de este idioma: "+ code);
    }


    public static String getNombreEnEspanolPorCodigo(String enumName){
        try {
            Idiomas idioma = Idiomas.valueOf(enumName.toUpperCase());
            return idioma.nombreIdioma;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No se encontró opción de este idioma: " + enumName, e);
        }
    }
}
