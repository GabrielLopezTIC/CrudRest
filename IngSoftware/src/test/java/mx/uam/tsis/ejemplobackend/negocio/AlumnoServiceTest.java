package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepositoryMock; // Mock generado por Mockito

    @InjectMocks
    private AlumnoService alumnoService; // La unidad a probar

    @Test
    public void testSuccesfulCreate() {

	Alumno alumno = new Alumno();
	alumno.setCarrera("Computación");
	alumno.setMatricula(12345678);
	alumno.setNombre("Pruebin");

	// Simula lo que haría el alumnoRepository real cuando le pasan una matricula de
	// alumno
	// que no ha sido guardado
	when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(null));

	when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);

	// Aqui ejecuto a la unidad que quiero probar
	alumno = alumnoService.create(alumno);

	// Aqui compruebo el resultado
	assertNotNull(alumno); // Probar que la referencia a alumno no es nula

    }

    @Test
    public void testUnsuccesfulCreate() {

	Alumno alumno = new Alumno();
	alumno.setCarrera("Computación");
	alumno.setMatricula(12345678);
	alumno.setNombre("Pruebin");

	// Simula lo que haría el alumnoRepository real cuando le pasan una matricula de
	// alumno
	// que ya ha sido guardado
	when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(alumno));

	// Aqui ejecuto a la unidad que quiero probar
	alumno = alumnoService.create(alumno);

	// Aqui compruebo el resultado
	assertNull(alumno); // Probar que la referencia a alumno es nula por que el alumno ya existía

    }

}
