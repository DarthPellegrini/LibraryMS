// default package
// Generated 28/11/2019 14:49:05 by Hibernate Tools 3.6.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Biblioteca generated by hbm2java
 */
public class Biblioteca implements java.io.Serializable {

	private int bibliotecaId;
	private Set<Estante> estantes = new HashSet<Estante>(0);

	public Biblioteca() {
	}

	public Biblioteca(int bibliotecaId) {
		this.bibliotecaId = bibliotecaId;
	}

	public Biblioteca(int bibliotecaId, Set<Estante> estantes) {
		this.bibliotecaId = bibliotecaId;
		this.estantes = estantes;
	}

	public int getBibliotecaId() {
		return this.bibliotecaId;
	}

	public void setBibliotecaId(int bibliotecaId) {
		this.bibliotecaId = bibliotecaId;
	}

	public Set<Estante> getEstantes() {
		return this.estantes;
	}

	public void setEstantes(Set<Estante> estantes) {
		this.estantes = estantes;
	}

}