// default package
// Generated 28/11/2019 14:49:05 by Hibernate Tools 3.6.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Evento generated by hbm2java
 */
public class Evento implements java.io.Serializable {

	private int eventoId;
	private LivroRetirado livroRetirado;
	private Date dataEvento;
	private String tipoEvento;
	private Set<LivroRetirado> livroRetiradosForEventoDevolucaoId = new HashSet<LivroRetirado>(0);
	private Set<LivroRetirado> livroRetiradosForEventoRetiradaId = new HashSet<LivroRetirado>(0);

	public Evento() {
	}

	public Evento(int eventoId, LivroRetirado livroRetirado, Date dataEvento, String tipoEvento) {
		this.eventoId = eventoId;
		this.livroRetirado = livroRetirado;
		this.dataEvento = dataEvento;
		this.tipoEvento = tipoEvento;
	}

	public Evento(int eventoId, LivroRetirado livroRetirado, Date dataEvento, String tipoEvento,
			Set<LivroRetirado> livroRetiradosForEventoDevolucaoId,
			Set<LivroRetirado> livroRetiradosForEventoRetiradaId) {
		this.eventoId = eventoId;
		this.livroRetirado = livroRetirado;
		this.dataEvento = dataEvento;
		this.tipoEvento = tipoEvento;
		this.livroRetiradosForEventoDevolucaoId = livroRetiradosForEventoDevolucaoId;
		this.livroRetiradosForEventoRetiradaId = livroRetiradosForEventoRetiradaId;
	}

	public int getEventoId() {
		return this.eventoId;
	}

	public void setEventoId(int eventoId) {
		this.eventoId = eventoId;
	}

	public LivroRetirado getLivroRetirado() {
		return this.livroRetirado;
	}

	public void setLivroRetirado(LivroRetirado livroRetirado) {
		this.livroRetirado = livroRetirado;
	}

	public Date getDataEvento() {
		return this.dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Set<LivroRetirado> getLivroRetiradosForEventoDevolucaoId() {
		return this.livroRetiradosForEventoDevolucaoId;
	}

	public void setLivroRetiradosForEventoDevolucaoId(Set<LivroRetirado> livroRetiradosForEventoDevolucaoId) {
		this.livroRetiradosForEventoDevolucaoId = livroRetiradosForEventoDevolucaoId;
	}

	public Set<LivroRetirado> getLivroRetiradosForEventoRetiradaId() {
		return this.livroRetiradosForEventoRetiradaId;
	}

	public void setLivroRetiradosForEventoRetiradaId(Set<LivroRetirado> livroRetiradosForEventoRetiradaId) {
		this.livroRetiradosForEventoRetiradaId = livroRetiradosForEventoRetiradaId;
	}

}
