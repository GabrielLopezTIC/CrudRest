package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Slf4j
@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private AlumnoService alumnoService;
	
	public Grupo create(Grupo nuevoGrupo) {
	    if(nuevoGrupo != null)
		return grupoRepository.save(nuevoGrupo);
	    else
		return null;
	}

	public Iterable <Grupo> findAll () {
		return grupoRepository.find();
	}
	
	public Optional<Grupo> findById(Integer id){
	    return grupoRepository.findById(id);
	}
	
	public Grupo update(Grupo grupo) {
	    if((grupoRepository.findById(grupo.getId()).isPresent())) {
		return grupoRepository.save(grupo);
	    }else {
		return null;
	    }
	    
	}
	
	public boolean delete(Integer id) {
	    if(grupoRepository.findById(id).isPresent()) {
		grupoRepository.delete(id);
		return true;
	    }else {
		return false;
	    }
	    
	}

	/**
	 * 
	 * Método que permite agregar un alumno a un grupo
	 * 
	 * @param groupId el id del grupo
	 * @param matricula la matricula del alumno
	 * @return true si se agregó correctamente, false si no
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		
		log.info("Agregando alumno con matricula "+matricula+" al grupo "+groupId);
		
		// 1.- Recuperamos primero al alumno
		Optional<Alumno> alumno = alumnoService.findByMatricula(matricula);
		
		// 2.- Recuperamos el grupo
		Optional <Grupo> grupoOpt = grupoRepository.findById(groupId);
		
		
		// 3.- Verificamos que el alumno y el grupo existan
		if(!grupoOpt.isPresent()) {
		    log.error("No se encontró grupo");
		    return false;
		}
		if(!alumno.isPresent()) {
		    log.error("No se encontró alumno");
		    return false;
		}
		
		log.info("Alumno recuperado: "+alumno.get().getNombre());
		log.info("Grupo recuperado: "+grupoOpt.get().getClave());
		
		// 4.- Agrego el alumno al grupo
		Grupo grupo = grupoOpt.get();
		log.info("Instancia grupo creada");
		
		grupo.addAlumno(alumno.get());
		
		log.info("Alumno "+alumno.get().getMatricula()+" agregado a "+grupo.getClave());
		
		// 5.- Persistir el cambio
		grupoRepository.save(grupo);
		
		return true;
	}

}
