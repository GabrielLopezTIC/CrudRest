package mx.uam.tsis.ejemplobackend.servicios;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import mx.uam.tsis.ejemplobackend.negocio.GrupoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@RestController
@RequestMapping("/v2")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @ApiOperation(value = "Crear grupo", notes = "Permite crear un nuevo grupo") // Documentacion del api
    @PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo) {
	Grupo grupo = null;
	if ((grupo =grupoService.create(nuevoGrupo)) != null) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
	} else {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se creo el grupo");
	}
    }

    @ApiOperation(value = "Mostrar Todos", notes = "Muestra la lista del total de grupose")
    @GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
	if (grupoService.findAll() != null) {
	    return ResponseEntity.status(HttpStatus.OK).body(grupoService.findAll());
	} else {
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

    }

    @ApiOperation(value = "Buscar grupo", notes = "Permite buscar un agrupo en base a su id")
    @GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieve(@PathVariable("id") Integer id) {
	if (grupoService.findById(id).isPresent()) {
	    return ResponseEntity.status(HttpStatus.OK).body(grupoService.findById(id));
	} else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
    }

    @ApiOperation(value = "Actualizar grupo", notes = "Permite actualizar los datos de un grupo")
    @PutMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid Grupo nuevoGrupo) {
	Grupo grupo = null;
	if ((grupo = grupoService.update(nuevoGrupo)) != null) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
	} else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
    }

    @ApiOperation(value = "Eliminar grupo", notes = "Permite eliminar un grupo en base a su id")
    @DeleteMapping("/grupos/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id)  {

	Optional<Grupo> grupo = grupoService.findById(id);

	
	if (!grupo.isPresent()) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El alumno no existe");
	}

	grupoService.delete(id);
	return ResponseEntity.status(HttpStatus.OK).body("Alumno borrado exitosamente");
    }

    /**
     * 
     * Método que permite agregar un alumno a un grupo
     * 
     * @param groupId   el id del grupo
     * @param matricula la matricula del alumno
     * @return true si se agregó correctamente, false si no
     */
    @ApiOperation(value = "Añadir alumno", notes = "Permite añadir un alumno al grupo")
    @PostMapping(path = "/grupos/{id}/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <?> addStudentToGroup(
		@PathVariable("id") Integer id,
		@RequestParam("matricula") Integer matricula) {
	if (grupoService.addStudentToGroup(id, matricula)) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.findById(id));
	} else {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
    }

}
