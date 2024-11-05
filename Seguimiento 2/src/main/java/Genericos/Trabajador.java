package Genericos;

import jakarta.persistence.*;

@Entity
@Table(name = "trabajadores")
public class Trabajador<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private T puesto;

    public Trabajador() {}

    public Trabajador(String nombre, T puesto) {
        this.nombre = nombre;
        this.puesto = puesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public T getPuesto() {
        return puesto;
    }

    public void setPuesto(T puesto) {
        this.puesto = puesto;
    }
}
