package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	private Livro livro; //referência ao livro
	private Cartao cartao; //referência ao cartão do cliente
	private Evento retirada; //evento de retirada do livro
	private List<Evento> renovacoes; //lista de renovações
	private Evento devolucao; //evento de devolução do livro
	private LocalDateTime dataDevolucao; //data em que a devolução deve ser feita
	
	public LivroRetirado(Livro livro, Cartao cartao, Evento retirada) {
		this.livro = livro;
		this.cartao = cartao;
		this.retirada = retirada;
		this.renovacoes = new ArrayList<Evento>();
		this.devolucao = null;
		this.dataDevolucao = retirada.getDataRaw().plusDays(7);
	}
	
	public LivroRetirado() {
		this.livro = new Livro();
		this.cartao = new Cartao();
		this.retirada = null;
		this.renovacoes = new ArrayList<Evento>();
		this.devolucao = null;
		this.dataDevolucao = null;
	}

	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Cartao getCartao() {
		return cartao;
	}
	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	public Evento getRetirada() {
		return retirada;
	}
	public LocalDate getDataRetiradaAsLocalDate() {
		if (retirada == null)
			return null;
		else
			return retirada.getDataRaw().toLocalDate();	
	}

	public int getTotalRenovacoes() {
		return this.renovacoes.size();
	}
	
	public Evento getLastRenovacao() {
		return this.renovacoes.get(renovacoes.size()-1);
	}
	
	public boolean addRenovacao(Evento evento) {
		if (this.renovacoes.size() < 3) {
			this.renovacoes.add(evento.copy());
			return true;
		} else return false;
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
	
	public void devolver(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		this.renovacoes.add(evento.copy());
		setDataDevolucao(
				Date.from(evento.getDataRaw().plusDays(7)
					.atZone(ZoneId.systemDefault()).toInstant()));
	}

	private void setDataDevolucao(Date data) {
		this.dataDevolucao = Instant.ofEpochMilli(data.getTime())
		        .atZone(ZoneId.systemDefault())
		        .toLocalDateTime();
		
	}
	
}
