package com.systemhaus.demo.domain;

public class Livro {

	//private int id; //único, código que identifica o livro na biblioteca (usado somente no BD)
	private String ISBN; //pode ser repetido, para n volumes do mesmo livro
	private int edicao; //edição do livro
	private String titulo; //título do livro
	private String autor; //autor do livro
	private String editora; //editora do livro
	private int numeroPaginas; //número de páginas
	private int quantCopias; //quantidade de livros no acervo
	private int quantDisp; //quantidade de livros disponíveis para a retirada
	
	public Livro(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantCopias) {
		super();
		setISBN(iSBN);
		setEdicao(edicao);
		setTitulo(titulo);
		setAutor(autor);
		setEditora(editora);
		setNumeroPaginas(numeroPaginas);
		setQuantCopias(quantCopias);
		setQuantDisp(quantCopias);
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		if (!iSBN.isEmpty() && iSBN.matches("97(8|9)[0-9]{10}")) {
			ISBN = iSBN;
		}
	}
	public int getEdicao() {
		return edicao;
	}
	public void setEdicao(int edicao) {
		if (edicao > 0)
			this.edicao = edicao;
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
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		if (numeroPaginas > 0)
			this.numeroPaginas = numeroPaginas;
	}
	public int getQuantCopias() {
		return quantCopias;
	}
	public void setQuantCopias(int quantCopias) {
		if (quantCopias > 0)
			this.quantCopias = quantCopias;
	}
	public int getQuantDisp() {
		return quantDisp;
	}
	public void setQuantDisp(int quantDisp) {
		if (quantDisp >= 0)
			this.quantDisp = quantDisp;
	}
	
	@Override
	public String toString() {
		return getISBN() + "-" + getAutor() + "-" + getTitulo() + "-" + getEditora() + "-" 
				+ getEdicao() + "-" + getNumeroPaginas() + "-" + getQuantCopias() + "-" + getQuantDisp();
	}
	
}
