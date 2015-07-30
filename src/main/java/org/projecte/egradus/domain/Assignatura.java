package org.projecte.egradus.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.projecte.egradus.utilities.Format;

/**
 * Classe de domini Assignatura.
 * Engloba les característiques bàsiques d'una assignatura.
 * (nom, descripció, claus per accedir-hi com a alumne i 
 * com a professor, etc)
 * Dins d'una assignatura hi podran haver tant alumnes com
 * professors, però cada rol tendrà un paper distint en
 * l'assignatura.
 * Cada persona pot participar (ja sigui com alumne o com a
 * professor) a tantes assignatures com desitji, amb l'únic
 * impediment què no pot ser alumne i professor a la mateixa
 * assignatura.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name="t_assignatura")
public class Assignatura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	/**
	 * codi definit pel professor. No té perquè
	 * ser únic entre totes les assignatures
	 */
	@NotEmpty
	private String codiReferencia;
	
	@NotEmpty
    private String nom;
	
	@Length(max = 4000)
	@Column(length = 4000)
    private String descripcio;
	
    private String clauProfessor;
    
    private String clauAlumne;
    
    private Date dataAlta;
    
    @NotEmpty
    @Pattern(regexp = Format.FORMAT_ANY_ACADEMIC)
    private String anyAcademic;
    
    @ManyToOne
    @NotNull
    private Persona creador;
    
    @OneToMany(mappedBy = "assignatura")
    private List<Professor> professors;
	
    @OneToMany(mappedBy = "assignatura")
    private List<Alumne> alumnes;
    
    // Constructor
	public Assignatura(){}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public String getCodiReferencia() {
		return codiReferencia;
	}

	public void setCodiReferencia(String codiReferencia) {
		this.codiReferencia = codiReferencia;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getClauProfessor() {
		return clauProfessor;
	}

	public void setClauProfessor(String clauProfessor) {
		this.clauProfessor = clauProfessor;
	}

	public String getClauAlumne() {
		return clauAlumne;
	}

	public void setClauAlumne(String clauAlumne) {
		this.clauAlumne = clauAlumne;
	}

	public Date getDataAlta() {
		return dataAlta;
	}

	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}
	
	public String getAnyAcademic() {
		return anyAcademic;
	}

	public void setAnyAcademic(String anyAcademic) {
		this.anyAcademic = anyAcademic;
	}

	public Persona getCreador() {
		return creador;
	}

	public void setCreador(Persona creador) {
		this.creador = creador;
	}
	
	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	public List<Alumne> getAlumnes() {
		return alumnes;
	}

	public void setAlumnes(List<Alumne> alumnes) {
		this.alumnes = alumnes;
	}

	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "            + codi);
        buffer.append(", codi referència: " + codiReferencia);
        buffer.append(", nom: "             + nom);
        buffer.append(", descripció: "      + descripcio);
        buffer.append(", clau professor: "  + clauProfessor);
        buffer.append(", clau alumne: "     + clauAlumne);
        buffer.append(", data d'alta: "     + dataAlta);
        buffer.append(", any acadèmic: "    + anyAcademic);
        buffer.append(", creador: " 		+ creador.toString());
        return buffer.toString();
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codi == null) ? 0 : codi.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignatura other = (Assignatura) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}