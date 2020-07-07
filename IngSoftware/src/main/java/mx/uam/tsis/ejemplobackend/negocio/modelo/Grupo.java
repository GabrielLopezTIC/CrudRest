package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Grupos")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter 
public class Grupo implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7897909913739527058L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    
    @NotNull
    @NotBlank
    @Column(name = "clave")
    private String clave;
    
    @OneToMany(targetEntity = Alumno.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id") // No crea tabla intermedia	
    private List<Alumno> alumnos;
    
    public boolean addAlumno(Alumno alumno) {
	return alumnos.add(alumno);
    }

}
