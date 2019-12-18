package com.systemhaus.demo.domain;

import java.util.List;

public interface LivroRepository {
	
	public void save(Livro livro);
	public List<Livro> findBySimilarExample(Livro example, boolean findAvailable);
	public void editByExample(String iSBNOriginal, Livro livro);
	@Deprecated
	void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros, int delete);
	@Deprecated
	void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	@Deprecated
	void markBooksForDeletion(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	int returnBookCount(String iSBN);
	int returnAvailableBookCount(String iSBN);
	boolean allTheBooksAreAvailable(String iSBN);
	boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias);
	void deleteThisRegLivros(RegLivros reg);
	RegLivros findRegLivrosForThis(String isbn);
	void deleteAllTheseBooks(String iSBNOriginal);
	void deleteOnlyTheseBooks(String iSBNOriginal, int delete);
	
	
}
