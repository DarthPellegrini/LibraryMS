package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Evento {
	
	//registro de eventos
	private int id; //id
	private TipoEvento tipoEvento;
	private Date data; //data do evento
	
	public Evento() {
		super();
		this.setData(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		this.setTipoEvento(TipoEvento.RETIRADA);
	}
	
	public Evento(TipoEvento tipoEvento) {
		super();
		setTipoEvento(tipoEvento);
		this.data = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private Evento(TipoEvento tipoEvento, Date data) {
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
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public Date getData() {
		return data;
	}
	public LocalDateTime getDataRaw() {
		return Instant.ofEpochMilli(data.getTime())
		        .atZone(ZoneId.systemDefault())
		        .toLocalDateTime();
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public Evento copy() {
		return new Evento(this.tipoEvento, this.data);
	}
}
