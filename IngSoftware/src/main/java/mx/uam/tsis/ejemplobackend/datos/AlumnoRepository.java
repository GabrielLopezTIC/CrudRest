package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Se encarga de almacenar y recuperar alumnos
 * 
 * @author humbertocervantes
 *
 */
@Repository
public class AlumnoRepository {
	
	// La "base de datos"
	public Map <Integer, Alumno> alumnoRepository = new HashMap <>();

	/**
	 * Guarda en la BD
	 * 
	 * @param alumno
	 */
	public Alumno save(Alumno nuevoAlumno) {
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;
	}
	
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}
	
	public List <Alumno> find() {
		return new ArrayList <> (alumnoRepository.values());
	}
	
	// falta update
	public Alumno update(Alumno alumno) {
	    alumnoRepository.put(alumno.getMatricula(), alumno);
	    return alumno;
	}
	
	// falta delete
	public void delete(Integer matricula) {
	    Alumno alumno = alumnoRepository.get(matricula);
	    alumnoRepository.remove(alumno.getMatricula(), alumno);
	}
 
}
