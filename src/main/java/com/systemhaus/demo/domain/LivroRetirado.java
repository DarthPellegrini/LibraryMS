package com.systemhaus.demo.domain;

import java.time.LocalDateTime;

public class LivroRetirado {
	
	private Livro livro; //referência ao livro retirado
	private LocalDateTime dataRetirada; //data em que o livro foi retirado 
	private LocalDateTime dataDevolucao; //data em que o livro será devolvido 
	
	public LivroRetirado(Livro livro, LocalDateTime dataRetirada, LocalDateTime dataDevolucao) {
		super();
		this.livro = livro;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
	}
	
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}
	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
