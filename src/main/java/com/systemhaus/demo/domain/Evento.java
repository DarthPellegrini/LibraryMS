package com.systemhaus.demo.domain;

public class Evento {
	
	private String evento; //Nome do evento
	private String descr; //Descrição do evento
	
	public Evento(String evento, String descr) {
		super();
		this.evento = evento;
		this.descr = descr;
	}
	
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
}
