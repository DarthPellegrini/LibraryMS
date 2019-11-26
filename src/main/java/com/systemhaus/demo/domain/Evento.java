package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Evento {
	
	//registro de eventos
	private int id; //id
	private TipoEvento tipoEvento;
	private LocalDateTime data; //data do evento
	
	public Evento(TipoEvento tipoEvento) {
		super();
		setTipoEvento(tipoEvento);
		this.data = LocalDateTime.now();
	}
	
	private Evento(TipoEvento tipoEvento, LocalDateTime data) {
		super();
		this.tipoEvento = tipoEvento;
		this.data = data;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TipoEvento getTipoDeEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public Date getData() {
		return Date.from(this.data.atZone(ZoneId.systemDefault()).toInstant());
	}
	public LocalDateTime getDataRaw() {
		return data;
	}
	public void setData(Date data) {
		this.data = Instant.ofEpochMilli(data.getTime())
				        .atZone(ZoneId.systemDefault())
				        .toLocalDateTime();
	}
	
	public Evento copy() {
		return new Evento(this.tipoEvento, this.data);
	}
}
