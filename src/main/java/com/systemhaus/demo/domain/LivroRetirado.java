package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jgoodies.binding.beans.Model;

public class LivroRetirado extends Model{

	/*
	 * Serial e Bindings
	 */
	private static final long serialVersionUID = 1L;
	//private static final String PROPERTY_LIVRO = "livro";
	
	private int id;
	private Livro livro; //referência ao livro
	private Cliente cliente; //referência ao cliente
	private Evento retirada; //evento de retirada do livro
	private List<Evento> renovacoes; //lista de renovações
	private Evento devolucao; //evento de devolução do livro
	private Date dataDevolucao; //data em que a devolução deve ser feita
	
	public LivroRetirado() {
		this.setLivro(new Livro());
		this.setCliente(new Cliente());
		this.setRetirada(null);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		this.dataDevolucao = null;
	}
	
	public LivroRetirado(Livro livro, Cliente cliente) {
		this.setLivro(livro);
		this.setCliente(cliente);
		this.setRetirada(null);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		this.dataDevolucao = null;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Evento getRetirada() {
		return retirada;
	}
	public void setRetirada(Evento retirada) {
		this.retirada = retirada;
	}
	public void addRetirada(Evento retirada) {
		this.setRetirada(retirada);
		this.setDataDevolucao(Date.from(
						Instant.ofEpochMilli(
						retirada.getData().getTime())
						.atZone(ZoneId.systemDefault())
						.toLocalDateTime().plusDays(7)
						.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	public LocalDate getDataRetiradaAsLocalDate() {
		if (retirada == null)
			return null;
		else
			return retirada.getDataRaw().toLocalDate();	
	}
	public List<Evento> getRenovacoes() {
		return renovacoes;
	}

	public void setRenovacoes(List<Evento> renovacoes) {
		this.renovacoes = renovacoes;
	}
	public int getTotalRenovacoes() {
		return getRenovacoes().size();
	}
	
	public Evento getLastRenovacao() {
		return getRenovacoes().get(getRenovacoes().size()-1);
	}
	
	public Evento getDevolucao() {
		return devolucao;
	}
	
	public LocalDate getDataDevolucaoAsLocalDate() {
		if (devolucao == null)
			return null;
		else
			return devolucao.getDataRaw().toLocalDate();	
	}
	
	public Date getDataDevolucao() {
		return this.dataDevolucao;
	}
	
	public LocalDate getDataDevolucaoRealAsLocalDate() {
		if (dataDevolucao == null)
			return null;
		else 
			return Instant.ofEpochMilli(dataDevolucao.getTime())
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
	}
	
	public void setDevolucao(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		this.renovacoes.add(evento.copy());
		this.setDataDevolucao(evento.getData());
	}
	
	public void setDataDevolucao(Date date) {
		this.dataDevolucao = date;
	}

	
}
