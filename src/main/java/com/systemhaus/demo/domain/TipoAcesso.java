package com.systemhaus.demo.domain;

public class TipoAcesso {

	private int id;
	private int nivelAcesso;
	private String descAcesso;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getNivelAcesso() {
		return nivelAcesso;
	}
	public void setNivelAcesso(Integer nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
	public String getDescAcesso() {
		return descAcesso;
	}
	public void setDescAcesso(String descAcesso) {
		this.descAcesso = descAcesso;
	}
	
}
