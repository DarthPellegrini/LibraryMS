package com.systemhaus.demo.domain;

public class TipoEvento {
	
	private String tipoEvento; //Nome do evento
	private String descr; //Descrição do evento
	
	public TipoEvento(String tipoEvento, String descr) {
		super();
		this.tipoEvento = tipoEvento;
		this.descr = descr;
	}
	
	public String gettipoEvento() {
		return tipoEvento;
	}
	public void settipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
}
