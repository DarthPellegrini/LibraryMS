package com.systemhaus.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	private LocalDateTime dataDevolucao; //data em que a devolução deve ser feita
	
	public LivroRetirado() {
		this.setLivro(new Livro());
		this.setCliente(new Cliente());
		this.setRetirada(null);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		this.dataDevolucao = null;
	}
	
	public LivroRetirado(Livro livro, Cliente cliente, Evento retirada) {
		this.setLivro(livro);
		this.setCliente(cliente);
		this.setRetirada(retirada);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		setDataDevolucao(retirada.getDataRaw());
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
	private void setRetirada(Evento retirada) {
		this.retirada = retirada;
	}
	public LocalDate getDataRetiradaAsLocalDate() {
		if (retirada == null)
			return null;
		else
			return retirada.getDataRaw().toLocalDate();	
	}
	private List<Evento> getRenovacoes() {
		return renovacoes;
	}

	private void setRenovacoes(List<Evento> renovacoes) {
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
	
	public LocalDateTime getDataDevolucao() {
		return this.dataDevolucao;
	}
	
	public LocalDate getDataDevolucaoRealAsLocalDate() {
		if (dataDevolucao == null)
			return null;
		else 
			return dataDevolucao.toLocalDate();
	}
	
	public void setDevolucao(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		this.renovacoes.add(evento.copy());
		this.setDataDevolucao(evento.getDataRaw());
	}
	
	public void setDataDevolucao(LocalDateTime date) {
		this.dataDevolucao = date.plusDays(7);
	}

	
}
