package com.systemhaus.demo.report;

public class LivroView {

	private String ISBN; 
	private String titulo;
	private String autor; 
	private String editora;
	private int edicao;
	private int numeroPaginas;
	private int quantLivrosNoCatalogo;
	private int quantLivrosParaRetirar;
	
	public LivroView () {
		super();
	}
	
	public LivroView(String iSBN, String titulo, String autor, String editora, int edicao, int numeroPaginas,
			int quantLivrosNoCatalogo, int quantLivrosParaRetirar) {
		super();
		ISBN = iSBN;
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.edicao = edicao;
		this.numeroPaginas = numeroPaginas;
		this.quantLivrosNoCatalogo = quantLivrosNoCatalogo;
		this.quantLivrosParaRetirar = quantLivrosParaRetirar;
	}

	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public int getEdicao() {
		return edicao;
	}
	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public int getQuantLivrosNoCatalogo() {
		return quantLivrosNoCatalogo;
	}
	public void setQuantLivrosNoCatalogo(int quantLivrosNoCatalogo) {
		this.quantLivrosNoCatalogo = quantLivrosNoCatalogo;
	}
	public int getQuantLivrosParaRetirar() {
		return quantLivrosParaRetirar;
	}
	public void setQuantLivrosParaRetirar(int quantLivrosParaRetirar) {
		this.quantLivrosParaRetirar = quantLivrosParaRetirar;
	}
	
}
