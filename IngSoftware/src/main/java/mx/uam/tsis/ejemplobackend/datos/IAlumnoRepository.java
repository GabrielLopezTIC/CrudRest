package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

public interface IAlumnoRepository extends CrudRepository<Alumno,Integer> {
    
   // public Alumno findByMatricula(Integer matricula);
    
   /* @Modifying
    @Transactional
    @Query("delete from Alumno a where matricula = ?1")
    public void deleteByMatricula(Integer matricula);*/
    
}
