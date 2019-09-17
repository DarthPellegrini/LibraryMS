package com.systemhaus.demo.domain;

import java.time.LocalDate;

public class LivroRetirado {
	
	private Livro livro; //referência ao livro retirado
	private LocalDate dataRetirada; //data em que o livro foi retirado 
	private LocalDate dataDevolucao; //data em que o livro será devolvido 
	
	public LivroRetirado(Livro livro, LocalDate dataRetirada, LocalDate dataDevolucao) {
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
	public LocalDate getDataRetirada() {
		return dataRetirada;
	}
	public void setDataRetirada(LocalDate dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
