package mx.uam.tsis.ejemplobackend.datos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Repository
public class GrupoRepository {
	

    @Autowired
    private IGrupoRepository repo;
    
    
	/**
	 * Guarda en la BD
	 * 
	 * @param alumno
	 */
	public Grupo save(Grupo nuevoGrupo) {
		return repo.save(nuevoGrupo);
	}
	
	public Optional<Grupo> findById(Integer id) {
		return repo.findById(id);
	}
	
	public Iterable <Grupo> find() {
		return repo.findAll();
	}
	
	// falta update
	public Grupo update(Grupo grupo) {
	    return repo.save(grupo);
	}
	
	// falta delete
	public void delete(Integer id) {
	    repo.deleteById(id);
	}
 
}
