package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Cartao {

	private String codigo; //codigo unico do cartao
	private LocalDateTime validade; //validade do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(String codigo, LocalDateTime validade) {
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
		return Date.from(this.validade.atZone(ZoneId.systemDefault()).toInstant());
	}
	public void setValidade(Date validade) {
		this.validade = Instant.ofEpochMilli(validade.getTime())
				        .atZone(ZoneId.systemDefault())
				        .toLocalDateTime();
	}
	public ArrayList<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}
	public void setLivrosRetirados(ArrayList<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}

	public final String createNewValidCodCartao() {
		String codigo = "";
		for(int i = 0; i < 4; i++) {
			String ranInt = "" + new Random().nextInt(10000);
			for(int j = 0; j < (4-ranInt.length()); j++)
				codigo += "";
			codigo += ranInt;
		}
		return codigo;
	}
	
	public void clear() {
		this.codigo = createNewValidCodCartao();
		this.validade = LocalDateTime.now().plusYears(4);
	}

	public boolean validate() {
		return (codigo.length() == 16 && validade.isAfter(LocalDateTime.now()));
	}
	
	
}
