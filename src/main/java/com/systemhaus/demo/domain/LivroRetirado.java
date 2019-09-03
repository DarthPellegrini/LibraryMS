package com.systemhaus.demo.domain;

public class LivroRetirado {
	
	private Livro livro; //referência ao livro editado
	private String dataRetirada; //data em que o livro foi retirado 
	private String dataDevolucao; //data em que o livro será devolvido 
	
	public LivroRetirado(Livro livro, String dataRetirada, String dataDevolucao) {
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
	public String getDataRetirada() {
		return dataRetirada;
	}
	public void setDataRetirada(String dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
	public String getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
