package com.systemhaus.demo.domain;

import java.time.LocalDateTime;

public class Evento {
	
	//registro de eventos
	private TipoEvento tipoEvento; //tipo de evento relacionado ao livro
	private LocalDateTime data; //data do evento
	
	public Evento(TipoEvento tipoEvento) {
		super();
		this.tipoEvento = tipoEvento;
		this.data = LocalDateTime.now();
	}
	
	private Evento(TipoEvento tipoEvento, LocalDateTime data) {
		super();
		this.tipoEvento = tipoEvento;
		this.data = data;
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
	
	public Evento copy() {
		return new Evento(this.tipoEvento, this.data);
	}
}
