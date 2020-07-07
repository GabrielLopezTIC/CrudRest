package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

public interface IGrupoRepository extends CrudRepository<Grupo,Integer>{
    

}
