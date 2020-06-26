package mx.uam.tsis.ejemplobackend.servicios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    /**
     * Create
     * 
     * @param nuevoAlumno
     * @return
     */
    @PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid Alumno nuevoAlumno) {

	log.info("Recibí llamada a create con " + nuevoAlumno);

	Alumno alumno = alumnoService.create(nuevoAlumno);

	if (alumno != null) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
	} else {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear alumno");
	}
    }

    /**
     * FindAll
     * 
     * @return
     */
    @GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieveAll() {

	List<Alumno> result = alumnoService.retrieveAll();

	return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    /**
     * Find by matr
     * 
     * @param matricula
     * @return
     */
    @GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieve(@PathVariable("matricula") Integer matricula) {
	log.info("Buscando al alumno con matricula " + matricula);
	return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findByMatricula(matricula));

    }

    /**
     * Update
     * 
     * @param matricula
     * @param alumno
     * @return
     */
    @PutMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid Alumno alumno) {

	log.info("Actualizando alumno : " + alumno.getMatricula());

	Alumno nuevoAlumno = alumnoService.findByMatricula(alumno.getMatricula());

	if (nuevoAlumno == null) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El alumno no existe");
	}
	nuevoAlumno.setMatricula(alumno.getMatricula());
	nuevoAlumno.setNombre(alumno.getNombre());
	nuevoAlumno.setCarrera(alumno.getCarrera());
	alumnoService.update(nuevoAlumno);
	return ResponseEntity.status(HttpStatus.OK).body(nuevoAlumno);
	
    }

    /**
     * Delete
     * 
     * @param matricula
     * @return
     */
    @DeleteMapping("/alumnos/{matricula}")
    public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
	log.info("Borrando alumno matricula: "+matricula);

	Alumno alumno = alumnoService.findByMatricula(matricula);

	if (alumno == null) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El alumno no existe");
	}

	alumnoService.delete(matricula);
	return ResponseEntity.status(HttpStatus.OK).body("Alumno borrado exitosamente");
    }

}
