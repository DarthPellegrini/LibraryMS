package com.systemhaus.demo.domain;

public interface EstanteRepository {
	
	public abstract Prateleira getPrateleiraWithEmptySpace();
	public abstract void addEstante();
	public abstract long getCountOfEstantes();
	public abstract boolean needsReorganization();
	public abstract void organizeLibrary();
	public abstract boolean addBook(Livro livro);
}
