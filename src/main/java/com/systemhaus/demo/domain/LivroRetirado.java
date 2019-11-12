package com.systemhaus.demo.domain;

import java.time.Instant;
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
		this.dataDevolucao = retirada.getDataRaw().plusDays(7);
	}
	
	public LivroRetirado() {
		this.livro = new Livro();
		this.cartao = new Cartao();
		this.retirada = new Evento(new TipoEvento("",""));
		this.renovacoes = new ArrayList<Evento>();
		this.devolucao = new Evento(new TipoEvento("",""));
		this.dataDevolucao = LocalDateTime.now();
		livro.clear();
		cartao.clear();
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
	public List<Evento> getRenovacoes() {
		return renovacoes;
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
	public Date getDataDevolucao() {
		return Date.from(this.dataDevolucao.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public void devolver(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		getRenovacoes().add(evento.copy());
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
