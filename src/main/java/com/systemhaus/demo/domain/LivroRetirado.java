package com.systemhaus.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

public class LivroRetirado {
	
	private Livro livro; //referência ao livro retirado
	private Cartao cartao; //cartão do cliente que retirou o livro
	private List<RegistroDeEvento> registroDeEventos; //registro dos eventos ocorridos com o livro
	private LocalDateTime dataRetirada; //data em que o livro foi retirado 
	private LocalDateTime dataDevolucao; //data em que o livro será devolvido 
	
	public LivroRetirado(Livro livro, Cartao cartao, List<RegistroDeEvento> registroDeEventos, LocalDateTime dataRetirada,
			LocalDateTime dataDevolucao) {
		this.livro = livro;
		this.cartao = cartao;
		this.registroDeEventos = registroDeEventos;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
	}
	
	public Cartao getCartao() {
		return cartao;
	}
	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	public List<RegistroDeEvento> getRegistroEvento() {
		return registroDeEventos;
	}
	public void setRegistroEvento(List<RegistroDeEvento> registroDeEventos) {
		this.registroDeEventos = registroDeEventos;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}
	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
