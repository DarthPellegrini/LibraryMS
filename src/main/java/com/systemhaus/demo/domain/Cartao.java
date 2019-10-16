package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Cartao {

	private String codigo; //codigo unico do cartao
	private LocalDate validade; //validade do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(String codigo, LocalDate validade) {
		this.codigo = codigo;
		this.validade = validade;
		livrosRetirados = new ArrayList<LivroRetirado>();
	}
	
	public Cartao() {
		this.clear();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		if(codigo.matches("[0-9]{16}"))
			this.codigo = codigo;
	}
	public Date getValidade() {
		return Date.from(this.validade.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	public void setValidade(Date validade) {
		this.validade = Instant.ofEpochMilli(validade.getTime())
				        .atZone(ZoneId.systemDefault())
				        .toLocalDate();
	}
	public ArrayList<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}
	public void setLivrosRetirados(ArrayList<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}

	public void clear() {
		this.codigo = "";
		this.validade = LocalDate.now();
	}
	
	
}
