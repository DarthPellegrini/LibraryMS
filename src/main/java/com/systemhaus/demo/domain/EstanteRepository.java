package com.systemhaus.demo.domain;

public interface EstanteRepository {
	
	public Prateleira getPrateleiraWithEmptySpace();
	public void addEstante();
	public long getCountOfEstantes();
	public boolean needsReorganization();
	public void organizeLibrary();
	boolean addBook(Livro livro);
}
