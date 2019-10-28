package com.systemhaus.demo.domain;

import java.time.LocalDateTime;

public class Evento {
	
	//registro de eventos
	private Livro livro; //referência ao livro
	private Cartao cartao; //referência ao cartão do cliente
	private TipoEvento tipoEvento; //tipo de evento relacionado ao livro
	private LocalDateTime data; //data do evento
	
	public Evento(Livro livro, Cartao cartao, TipoEvento tipoEvento) {
		super();
		this.livro = livro;
		this.cartao = cartao;
		this.tipoEvento = tipoEvento;
		data = LocalDateTime.now();
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
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
}
