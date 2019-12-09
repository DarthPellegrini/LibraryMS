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
	private LivroRetirado livroRetirado;
	
	public Evento() {
		super();
		this.setData(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		this.setTipoEvento(TipoEvento.RETIRADA);
	}
	
	public Evento(TipoEvento tipoEvento, LivroRetirado livroRetirado) {
		super();
		setTipoEvento(tipoEvento);
		this.data = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		this.livroRetirado = livroRetirado;
	}
	
	private Evento(TipoEvento tipoEvento, Date data, LivroRetirado livroRetirado) {
		super();
		this.tipoEvento = tipoEvento;
		this.data = data;
		this.livroRetirado = livroRetirado;
		
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
	
	public LivroRetirado getLivroRetirado() {
		return livroRetirado;
	}

	public void setLivroRetirado(LivroRetirado livroRetirado) {
		this.livroRetirado = livroRetirado;
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
		return new Evento(this.tipoEvento, this.data, this.livroRetirado);
	}
}
