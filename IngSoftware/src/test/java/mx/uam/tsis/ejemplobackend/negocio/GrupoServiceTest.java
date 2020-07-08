package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
public class GrupoServiceTest {

    @Mock
    private GrupoRepository grupoRepositoryMock;

    @Mock
    private AlumnoService alumnoServiceMock;

    @InjectMocks
    private GrupoService grupoService;

    //// Create
    @Test
    public void testSuccesfulCreate() {
	// grupo no nulo
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

	assertEquals(Optional.of(grupo), grupoService.create(grupo));
    }

    @Test
    public void testUnSuccesfulCreate() {
	// grupo nullo
	Grupo grupo = null;
	assertEquals(Optional.empty(), grupoService.create(grupo));
    }

    ///// FindAll

    @Test
    public void testSuccesfulFindAll() {

	List<Grupo> grupos = new ArrayList<Grupo>();

	when(grupoRepositoryMock.find()).thenReturn(grupos);

	Optional<Iterable<Grupo>> grupOpt = Optional.of(grupos);

	assertEquals(grupOpt, grupoService.findAll());

    }

    /// FindById
    @Test
    public void testSuccesfulFindById() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	Optional<Grupo> grupOpt = Optional.of(grupo);

	// matricula existente
	when(grupoRepositoryMock.findById(1)).thenReturn(grupOpt);

	assertEquals(grupOpt, grupoService.findById(1));

    }

    @Test
    public void testUnSuccesfullFindById() {

	Optional<Grupo> grupOpt = Optional.empty();

	// matriucula nulla
	assertEquals(grupOpt, grupoService.findById(null));
    }
    // Update

    /**
     * Caso si existe el grupo que queremos actualizar
     */
    @Test
    public void testSuccesfulUpdate() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	Optional<Grupo> grupOpt = Optional.of(grupo);

	when(grupoRepositoryMock.findById(1)).thenReturn(grupOpt);
	when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

	assertEquals(grupOpt, grupoService.update(grupo));

    }

    /**
     * Caso: No existe el grupo que queremos actualizar
     */
    @Test
    public void testUnSuccesfulUpdate() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	Optional<Grupo> grupOpt = Optional.empty();

	when(grupoRepositoryMock.findById(1)).thenReturn(grupOpt);

	assertEquals(grupOpt, grupoService.update(grupo));

    }

    /// Delete
    /**
     * Caso: Si existe el grupo que queremos eliminar
     */
    @Test
    public void testSuccesfulDelete() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	Optional<Grupo> grupOpt = Optional.of(grupo);

	when(grupoRepositoryMock.findById(1)).thenReturn(grupOpt);

	assertEquals(true, grupoService.delete(1));

    }

    /**
     * Caso: No existe el grupo que queremos eliminar
     */
    @Test
    public void testUnSuccesfulDelete() {
	Optional<Grupo> boletoOpt = Optional.empty();

	when(grupoRepositoryMock.findById(1)).thenReturn(boletoOpt);

	assertEquals(false, grupoService.delete(1));

    }

    @Test
    public void testSuccesfulAddStudentToGroup() {

	List<Alumno> alumnos = new ArrayList<Alumno>();

	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setAlumnos(alumnos);
	grupo.setClave("TST01");

	Alumno alumno = new Alumno();
	alumno.setCarrera("Computación");
	alumno.setMatricula(12345678);
	alumno.setNombre("Pruebin");

	Optional<Alumno> alOpt = Optional.of(alumno);

	// Stubbing para el alumnoService
	when(alumnoServiceMock.findByMatricula(12345678)).thenReturn(alOpt);

	// Stubbing para grupoRepository
	when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));

	boolean result = grupoService.addStudentToGroup(1, 12345678);

	assertEquals(true, result);

	assertEquals(grupo.getAlumnos().get(0), alumno);

    }

    @Test
    public void testUnsuccesfulAddStudentToGroup() {

	Alumno alumno = new Alumno();
	alumno.setCarrera("Computación");
	alumno.setMatricula(12345678);
	alumno.setNombre("Pruebin");

	// Stubbing para el alumnoService
	when(alumnoServiceMock.findByMatricula(12345678)).thenReturn(Optional.of(alumno));

	Optional<Grupo> grupo = Optional.empty();
	// Stubbing para grupoRepository
	when(grupoRepositoryMock.findById(1)).thenReturn(grupo);

	boolean result = grupoService.addStudentToGroup(1, 12345678);

	assertEquals(false, result);

    }
}
