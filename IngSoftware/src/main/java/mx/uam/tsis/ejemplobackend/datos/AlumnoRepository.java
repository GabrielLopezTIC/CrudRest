package mx.uam.tsis.ejemplobackend.datos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Se encarga de almacenar y recuperar alumnos
 * 
 * @author humbertocervantes
 *
 */
@Repository
public class AlumnoRepository{
	

    @Autowired
    private IAlumnoRepository repo;
    
    
	/**
	 * Guarda en la BD
	 * 
	 * @param alumno
	 */
	public Alumno save(Alumno nuevoAlumno) {
		return repo.save(nuevoAlumno);
	}
	
	public Optional<Alumno> findById(Integer matricula) {
		return repo.findById(matricula);
	}
	
	public Iterable <Alumno> find() {
		return repo.findAll();
	}
	
	// falta update
	public Alumno update(Alumno alumno) {
	    return repo.save(alumno);
	}
	
	// falta delete
	public void delete(Integer matricula) {
	    repo.deleteById(matricula);
	}
 
}
