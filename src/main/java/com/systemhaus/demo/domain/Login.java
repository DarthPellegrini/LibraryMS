package com.systemhaus.demo.domain;

public class Login {

	private int id;
	private String user; //nome do usuário
	private String pass; //senha (totalmente visívil, método NÃO RECOMENDADO, somente será feito assim por ser um projeto pequeno, sem uso no mundo real
	private TipoAcesso tipoAcesso; //nível de autenticação
	private Boolean logged = false;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}
	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
	public Boolean isLogged() {
		return logged;
	}
	public void setLogged(Boolean logged) {
		this.logged = logged;
	}	
	
}
