package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Alumnos")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter 
public class Alumno implements Serializable {
    
   
    private static final long serialVersionUID = 8044580103807801298L;

    @Id
    @NotNull
    @Column(name = "matricula")
    private Integer matricula;

    @NotNull
    @NotBlank
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @NotBlank
    @Column(name = "carrera")
    private String carrera;
}
