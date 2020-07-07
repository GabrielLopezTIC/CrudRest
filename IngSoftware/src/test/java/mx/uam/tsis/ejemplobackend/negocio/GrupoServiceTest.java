package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	grupo = grupoService.create(grupo);
    }

    @Test
    public void testUnSuccesfulCreate() {
	Grupo grupo = null;
	grupo = grupoService.create(grupo);
    }

    ///// FindAll
    @Test
    public void testSuccesfulFindAll() {
	Iterable<Grupo> iteratorMock = null;

	when(grupoRepositoryMock.find()).thenReturn(iteratorMock);

	iteratorMock = grupoService.findAll();

    }

    ///FindById
    @Test
    public void testSuccesfulFindById() {

	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	Optional<Grupo> grup = Optional.of(grupo);

	when(grupoRepositoryMock.findById(1)).thenReturn(grup);

	grup = grupoService.findById(1);
    }
    
    //Update
    
    /**
     * Caso si existe el grupo que queremos actualizar
     */
    @Test
    public void testSuccesfulUpdate() {

	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	Optional<Grupo> grup = Optional.of(grupo);
	
	when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
	when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(grup);

	grupo = grupoService.update(grupo);
    }
    
    /**
     * Caso: No existe el grupo que queremos actualizar
     */
    @Test
    public void testUnSuccesfulUpdate() {

	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	
	
	Optional<Grupo> grup = Optional.empty();
	
	when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(grup);
	
	 assertNull(grupoService.update(grupo));
    }

    ///Delete
    /**
     * Caso:  Si existe el grupo que queremos eliminar
     */
    @Test
    public void testSuccesfulDelete() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	Optional<Grupo> grup = Optional.of(grupo);

	when(grupoRepositoryMock.findById(1)).thenReturn(grup);
	assertTrue(grupoService.delete(1));
    }
    
    /**
     * Caso:  No existe el grupo que queremos eliminar
     */
    @Test
    public void testUnSuccesfulDelete() {
	Grupo grupo = new Grupo();
	grupo.setId(1);
	grupo.setClave("TST01");
	Optional<Grupo> grup = Optional.empty();

	when(grupoRepositoryMock.findById(1)).thenReturn(grup);
	assertFalse(grupoService.delete(1));
    }

}
