package mx.uam.tsis.ejemplobackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    private Alumno alumno;
    private Grupo grupo;
    private Grupo grupo2;

    @BeforeEach
    public void prepare() {

	// creamos un alumno que estara guardado en la base de datos inicialmente
	alumno = new Alumno();
	alumno.setCarrera("Computación");
	alumno.setMatricula(12345678);
	alumno.setNombre("Pruebin");
	
	

	// creamos un grupo que estara guardado inicialmente en la base de datos
	grupo = Grupo.builder().id(1).clave("A333").build();
	
	//creamos otro grupo para probar el delete
	grupo2 = Grupo.builder().id(10).clave("B333").build();

	// guardo el alumno y grupo en la base de datos
	alumnoRepository.save(alumno);
	grupoRepository.save(grupo);
	grupoRepository.save(grupo2);
    }

    // addStudentTo group
    @Test
    public void testAddStudentSuccesfull() {

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// proporcionamos un alumno existente en el request param
	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos/1/alumnos?matricula=12345678",
		HttpMethod.POST, request, Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	log.info(""+responseEntity.getStatusCode());
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

	// comparo el id de alumno con el id del alumno en el grupo
	assertEquals(alumno.getMatricula(), responseEntity.getBody().getAlumnos().get(0).getMatricula());

    }

    @Test
    public void testAddStudentToGroupUnSucessfull() {

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// mandamos matricula de un alumno inexistente
	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos/1/alumnos?matricula=12345679",
		HttpMethod.POST, request, Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    // create group

    @Test
    public void testCreateGroupSuccesfull() {
	// Creo el alumno que voy a enviar
	Grupo grupoNuevo = Grupo.builder().clave("A334").id(2).build();

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(grupoNuevo, headers);

	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos", HttpMethod.POST, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

	Optional<Grupo> grupoBusc = grupoRepository.findById(2);
	// grupo = grupoBusc.get();

	assertEquals(grupoBusc.get().getId(), responseEntity.getBody().getId());
    }

    @Test
    public void testCreateGroupUnSuccesfull() {
	// Enviaremos un grupo nulo
	Grupo grupoNuevo = null;

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(grupoNuevo, headers);

	// grupo existente
	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos/", HttpMethod.POST, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    // Find group

    @Test
    public void testFindAllSuccesfull() {
	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	ResponseEntity<Grupo[]> responseEntity = restTemplate.exchange("/v2/grupos/", HttpMethod.GET, request,
		Grupo[].class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFindByIdSuccesfull() {
	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// pasamos un id existente
	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos/1", HttpMethod.GET, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFindByIdUnSuccesfull() {
	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// pasamos un id no existente
	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos/20", HttpMethod.GET, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // update

    @Test
    public void testUpdateSuccesfull() {
	// Creo un alumno con una matricula existente
	Grupo grupoNuevo = Grupo.builder().clave("A335").id(1).build();

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(grupoNuevo, headers);

	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos", HttpMethod.PUT, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

	// verifico el cambio la clave del
	assertEquals(grupoNuevo.getClave(), responseEntity.getBody().getClave());
    }

    @Test
    public void testUpdateUnSuccesfull() {

	// Creo un alumno con una matricuka que no existe
	Grupo grupoNuevo = Grupo.builder().clave("A335").id(20).build();

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(grupoNuevo, headers);

	ResponseEntity<Grupo> responseEntity = restTemplate.exchange("/v2/grupos", HttpMethod.PUT, request,
		Grupo.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    // Delete
    @Test
    public void testDeleteSuccesful() {
	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// id existente
	ResponseEntity<String> responseEntity = restTemplate.exchange("/v2/grupos/10", HttpMethod.DELETE, request,
		String.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUnSuccesful() {

	// Creo el encabezado
	HttpHeaders headers = new HttpHeaders();
	headers.set("content-type", MediaType.APPLICATION_JSON.toString());

	// Creo la petición con el alumno como body y el encabezado
	HttpEntity<Grupo> request = new HttpEntity<>(headers);

	// id existente
	ResponseEntity<String> responseEntity = restTemplate.exchange("/v2/grupos/20", HttpMethod.DELETE, request,
		String.class);

	// Corroboro que el endpoint me regresa el estatus esperado
	assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }
}
