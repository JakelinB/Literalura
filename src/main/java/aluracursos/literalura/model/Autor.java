package aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "nombre", unique = true, nullable = false)
        private String nombre;
        private String fechaDeNacimiento;

        private String fechaDeFallecimiento;
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(
                name = "libros_autores",
                joinColumns = @JoinColumn(name = "autor_id"),
                inverseJoinColumns = @JoinColumn(name = "libro_id")
        )
        private List<Libros> libros = new ArrayList<>();
        public Autor() {
        }
        public Autor(DatosAutor datos) {
            this.nombre = datos.nombre();
            this.fechaDeNacimiento = datos.fechaDeNacimiento();
            this.fechaDeFallecimiento = datos.fechaDeFallecimiento();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getFechaDeNacimiento() {
            return fechaDeNacimiento;
        }

        public void setFechaDeNacimiento(String fechaDeNacimiento) {
            this.fechaDeNacimiento = fechaDeNacimiento;
        }

        public String getFechaDeFallecimiento() {
            return fechaDeFallecimiento;
        }

        public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
            this.fechaDeFallecimiento = fechaDeFallecimiento;
        }


        public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "\n***********Autores****************" +
                "\nnombre: " + nombre +
                "\nfechaDeNacimiento= " + fechaDeNacimiento +
                "\nfechaDeFallecimiento='" + fechaDeFallecimiento +
                "\n***********Autores****************\n";
    }
}
