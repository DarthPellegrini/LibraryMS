package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Evento {
	
	//registro de eventos
	private int id; //id
	private enum tiposDeEvento {RETIRADA,RENOVACAO,DEVOLUCAO}; //tipo de evento relacionado ao livro
	private tiposDeEvento tipoEvento;
	private LocalDateTime data; //data do evento
	
	public Evento(int tipo) {
		super();
		setTipoEvento(tipo);
		this.data = LocalDateTime.now();
	}
	
	private Evento(tiposDeEvento tipoEvento, LocalDateTime data) {
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
	public tiposDeEvento getTipoDeEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(int tipo) {
		switch(tipo) {
			case 0:
				this.tipoEvento = tiposDeEvento.RETIRADA;
				break;
			case 1:
				this.tipoEvento = tiposDeEvento.RENOVACAO;
				break;
			case 2:
				this.tipoEvento = tiposDeEvento.DEVOLUCAO;
				break;
		}
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
