package com.systemhaus.demo.domain;

import java.time.LocalDate;

public class Registro {
	
	//registro de eventos
	
	private Livro livro; //referência ao livro que foi modificado
	private Evento evento; //tipo de evento relacionado ao livro
	private LocalDate data; //data do evento
	private Cliente cliente; //opcional, caso um cliente envolvido em um evento (retirada/devolucao)
	
	public Registro(Livro livro, Evento evento, LocalDate data) {
		super();
		this.livro = livro;
		this.setEvento(evento);
		this.data = data;
	}
	
	public Registro(Livro livro, Evento evento, LocalDate data, Cliente cliente) {
		super();
		this.livro = livro;
		this.setEvento(evento);
		this.data = data;
		this.setCliente(cliente);
	}
	
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
