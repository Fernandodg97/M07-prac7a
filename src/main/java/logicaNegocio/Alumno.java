package logicaNegocio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EntityManager;

@Entity
@Table(name = "alumnos") // Mapea con la tabla 'alumnos' en la BD
public class Alumno {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "curso", nullable = false, length = 50)
    private String curso;

    // Constructor vacío (requerido por JPA)
    public Alumno() {}

    // Constructor con parámetros
    public Alumno(int id, String nombre, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
    }

    // Métodos getters y setters
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    // Método para guardar un alumno con JPA
    public static void guardarAlumnoJPA(EntityManager entityManager, int id, String nombre, String curso) {
        Alumno alumnoExistente = entityManager.find(Alumno.class, id);
        
        if (alumnoExistente == null) {
            Alumno nuevoAlumno = new Alumno(id, nombre, curso);
            entityManager.getTransaction().begin();
            entityManager.persist(nuevoAlumno);
            entityManager.getTransaction().commit();
            System.out.println("Alumno guardado con éxito.");
        } else {
            System.out.println("Error: El ID ya está en uso.");
        }
    }
}
