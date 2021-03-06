package com.systemhaus.demo.domain;

import com.jgoodies.binding.beans.Model;

public class Livro extends Model{

	/**
	 * Serial e bindings
	 */
	private static final long serialVersionUID = 1L;
	public static final String PROPERTY_ISBN = "ISBN";
	public static final String PROPERTY_EDICAO = "edicao";
	public static final String PROPERTY_TITULO = "titulo";
	public static final String PROPERTY_AUTOR = "autor";
	public static final String PROPERTY_EDITORA = "editora";
	public static final String PROPERTY_NUMPAG = "numeroPaginas";
	public static final String PROPERTY_RETIRADO = "retirado";
	
	private int id;
	private Prateleira prateleira;
	private String ISBN; //pode ser repetido, para n volumes do mesmo livro
	private String titulo; //título do livro
	private String autor; //autor do livro
	private String editora; //editora do livro
	private int edicao; //edição do livro
	private int numeroPaginas; //número de páginas
	private boolean retirado; //boolean que define se o livro está ou não retirado
	private boolean ativo; //boolean que define se o registro do livro está ativo (pode ser pesquisado)
	
	public Livro(String iSBN, String titulo, String autor, String editora, int edicao, int numeroPaginas, boolean retirado, Prateleira p) {
		super();
		this.setPrateleira(p);
		setISBN(iSBN);
		setTitulo(titulo);
		setAutor(autor);
		setEditora(editora);
		setEdicao(edicao);
		setNumeroPaginas(numeroPaginas);
		this.retirado = retirado;
		this.ativo = true;
	}
	
	public Livro(String iSBN, String titulo, String autor, String editora, int edicao, int numeroPaginas, boolean retirado) {
		super();
		setISBN(iSBN);
		setTitulo(titulo);
		setAutor(autor);
		setEditora(editora);
		setEdicao(edicao);
		setNumeroPaginas(numeroPaginas);
		this.retirado = retirado;
		this.ativo = true;
	}
	
	public Livro() {
		this.clear();
		this.ativo = true;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Prateleira getPrateleira() {
		return prateleira;
	}

	public void setPrateleira(Prateleira prateleira) {
		this.prateleira = prateleira;
	}

	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		if (!iSBN.isEmpty() && iSBN.matches("97(8|9)[0-9]{10}")) {
			String oldValue = this.ISBN;
            this.ISBN= iSBN;
            firePropertyChange(PROPERTY_ISBN, oldValue, this.ISBN);
		}
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		String oldValue = this.titulo;
        this.titulo = titulo;
        firePropertyChange(PROPERTY_TITULO, oldValue, this.titulo);
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		String oldValue = this.autor;
        this.autor = autor;
        firePropertyChange(PROPERTY_AUTOR, oldValue, this.autor);
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		String oldValue = this.editora;
        this.editora = editora;
        firePropertyChange(PROPERTY_EDITORA, oldValue, this.editora);
	}
	public int getEdicao() {
		return edicao;
	}
	public void setEdicao(int edicao) {
		int oldValue = this.edicao;
		if (edicao > 0) {
		    this.edicao = edicao;
        }else {
		    this.edicao = 1;
        }
        firePropertyChange(PROPERTY_EDICAO, oldValue, this.edicao);	
	}
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		int oldValue = this.numeroPaginas;
		if (numeroPaginas > 0) {
		    this.numeroPaginas = numeroPaginas;
		}else {
		    this.numeroPaginas = 1;
		}
		firePropertyChange(PROPERTY_NUMPAG, oldValue, this.numeroPaginas);
	}
	public boolean isRetirado() {
		return retirado;
	}
	public void setRetirado(boolean retirado) {
		boolean oldValue = this.retirado;
        this.retirado = retirado;
        firePropertyChange(PROPERTY_RETIRADO, oldValue, this.retirado);
	}
	public boolean getRetirado() {
		return retirado;
	}
	//valida os dados de um livro
	public boolean validate() {
		return (!ISBN.equals("") && edicao > 0 && numeroPaginas > 0) ? true: false;
	}
	
	@Override
	public String toString() {
		return getISBN() + "-" + getAutor() + "-" + getTitulo() + "-" + getEditora() + "-" 
				+ getEdicao() + "-" + getNumeroPaginas() + "-" + isRetirado();
	}
	
	/*
	 * Utilizado para a edição do livro:
	 * Copia todos os atributos de um livro
	 */
	public final void setAllDataFrom(Livro l) {
		if (!l.getISBN().isEmpty())  setISBN(l.getISBN());
		if (!l.getTitulo().isEmpty())  setTitulo(l.getTitulo());
		if (!l.getAutor().isEmpty())  setAutor(l.getAutor());
		if (!l.getEditora().isEmpty())  setEditora(l.getEditora());
		if (l.getEdicao() != 0)  setEdicao(l.getEdicao());
		if (l.getNumeroPaginas() != 0)	setNumeroPaginas(l.getNumeroPaginas());
		setPrateleira(l.getPrateleira());
	}
	
	/*
	 * Cria uma cópia do livro
	 */
	public Livro copy() {
		return new Livro(ISBN, titulo, autor, editora, edicao, numeroPaginas, retirado, prateleira);
	}
	
	public boolean isEqual(Livro l) {
		return (ISBN.equals(l.getISBN()) && titulo.equals(l.getTitulo()) && autor.equals(l.getAutor())
				&& editora.equals(l.getEditora()) && edicao == l.getEdicao() && numeroPaginas == l.getNumeroPaginas());
	}
	
	public void clear() {
		this.ISBN = "";
		this.titulo = "";
		this.autor = "";
		this.editora = "";
		this.edicao = 0;
		this.numeroPaginas = 0;
		this.retirado = false;
		this.ativo = true;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
