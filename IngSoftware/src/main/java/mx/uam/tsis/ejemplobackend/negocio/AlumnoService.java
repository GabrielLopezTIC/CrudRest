package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

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
		Optional <Alumno> alumno = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		
		if(!alumno.isPresent()) {
			return alumnoRepository.save(nuevoAlumno);
		} else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterable <Alumno> retrieveAll () {
		return alumnoRepository.find();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public Optional<Alumno> findByMatricula(Integer matricula){
	    return alumnoRepository.findById(matricula);
	}
	
	/**
	 * 
	 * @param alumno
	 * @return
	 */
	public Alumno update(Alumno alumno) {
	    if(alumnoRepository.findById(alumno.getMatricula()).isPresent())
		return alumnoRepository.save(alumno);
	    return null;
	}
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public boolean delete(Integer matricula) {
	    if(alumnoRepository.findById(matricula).isPresent()) {
		alumnoRepository.delete(matricula);
		return true;
	    }else {
		return false;
	    }
	    
	}
}
