package com.systemhaus.demo.domain;

import java.util.List;

public interface LivroRepository {
	
	public void save(Livro livro);
	public Livro findByExample(Livro livro);
	public void editByExample(String iSBNOriginal, Livro livro);
	void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros, int delete);
	void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	void markBooksForDeletion(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	
}
