package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Alumno alumno = alumnoRepository.findByMatricula(nuevoAlumno.getMatricula());
		
		if(alumno == null) {
			return alumnoRepository.save(nuevoAlumno);
		} else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List <Alumno> retrieveAll () {
		return alumnoRepository.find();
	}
	
	
	public Alumno findByMatricula(Integer matricula){
	    return alumnoRepository.findByMatricula(matricula);
	}
	
	/**
	 * 
	 * @param alumno
	 * @return
	 */
	public Alumno update(Alumno alumno) {
	    return alumnoRepository.save(alumno);
	}
	
	public void delete(Integer matricula) {
	    alumnoRepository.delete(matricula);
	}
}
