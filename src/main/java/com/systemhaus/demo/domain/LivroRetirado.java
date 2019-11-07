package com.systemhaus.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LivroRetirado {

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
		this.dataDevolucao = retirada.getData().plusDays(7);
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
	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void devolver(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		getRenovacoes().add(evento.copy());
		setDataDevolucao(evento.getData().plusDays(7));
	}

	private void setDataDevolucao(LocalDateTime data) {
		this.dataDevolucao = data;
		
	}
	
	
	
}
