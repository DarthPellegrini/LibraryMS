package com.systemhaus.demo.domain;

import java.util.List;

public interface LivroRepository {
	
	public abstract void save(Livro livro);
	public abstract List<Livro> findBySimilarExample(Livro example, boolean findAvailable, boolean isRetirado);
	public abstract void editByExample(String iSBNOriginal, Livro livro);
	@Deprecated
	public abstract void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros, int delete);
	@Deprecated
	public abstract void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	@Deprecated
	public abstract void markBooksForDeletion(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros);
	public abstract int returnBookCount(String iSBN);
	public abstract int returnAvailableBookCount(String iSBN);
	public abstract boolean allTheBooksAreAvailable(String iSBN);
	public abstract boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias);
	public abstract void deleteThisRegLivros(RegLivros reg);
	public abstract RegLivros findRegLivrosForThis(String isbn);
	public abstract void deleteAllTheseBooks(String iSBNOriginal);
	public abstract void deleteOnlyTheseBooks(String iSBNOriginal, int delete);
	public abstract void generateLivroReport();
	public abstract Livro initializeLivro(Livro livro);
	
	
}
