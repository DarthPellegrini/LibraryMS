package com.systemhaus.demo.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class Cartao {

	private String nome; //nome do cliente impresso no cartão
	private String codigo; //codigo unico do cartao
	private Date validade; //validade do cartao
	
	public Cartao(String nome, String codigo, LocalDateTime validade) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.validade = Date.from(validade.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public Cartao() {
		super();
		this.clear();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		if(codigo.matches("[0-9]{16}"))
			this.codigo = codigo;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public final String createNewValidCodCartao() {
		String codigo = "";
		while(codigo.length() < 16) {
			codigo = "";
			for(int i = 0; i < 4; i++) {
				String ranInt = "" + new Random().nextInt(10000);
				for(int j = 0; j < (4-ranInt.length()); j++)
					codigo += "0";
				codigo += ranInt;
			}
		}
		return codigo;
	}
	
	public void clear() {
		this.nome = "";
		this.codigo = "";
		this.validade = Date.from(LocalDateTime.now().plusYears(4).atZone(ZoneId.systemDefault()).toInstant());
	}

	public boolean validate() {
		return (codigo.length() == 16 && Instant.ofEpochMilli(validade.getTime())
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime().isAfter(LocalDateTime.now()));
	}

	public LocalDate getValidadeAsLocalDate() {
		return Instant.ofEpochMilli(validade.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}
	
	
}
