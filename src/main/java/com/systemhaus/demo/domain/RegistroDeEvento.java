package com.systemhaus.demo.domain;

import java.time.LocalDateTime;

public class RegistroDeEvento {
	
	//registro de eventos
	
	private LivroRetirado livro; //referência ao livro que foi modificado
	private Evento evento; //tipo de evento relacionado ao livro
	private LocalDateTime data; //data do evento
	
	public RegistroDeEvento(LivroRetirado livro, Evento evento, LocalDateTime data) {
		this.livro = livro;
		this.setEvento(evento);
		this.data = data;
	}
	  
	
	public LivroRetirado getLivro() {
		return livro;
	}
	public void setLivro(LivroRetirado livro) {
		this.livro = livro;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
}
