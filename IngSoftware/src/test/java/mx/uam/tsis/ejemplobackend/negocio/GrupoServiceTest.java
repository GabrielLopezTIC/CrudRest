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
	//grupo no nulo
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");

	when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

	assertEquals(Optional.of(grupo), grupoService.create(grupo));
    }

    @Test
    public void testUnSuccesfulCreate() {
	//grupo nullo
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

	//matricula existente
	when(grupoRepositoryMock.findById(1)).thenReturn(grupOpt);

	assertEquals(grupOpt, grupoService.findById(1));

    }

    @Test
    public void testUnSuccesfullFindById() {

	Optional<Grupo> grupOpt = Optional.empty();

	//matriucula nulla
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

}
