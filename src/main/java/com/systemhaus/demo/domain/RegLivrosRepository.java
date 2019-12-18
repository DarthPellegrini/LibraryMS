package com.systemhaus.demo.domain;

public interface RegLivrosRepository {

	public abstract int returnBookCount(String iSBN);
	public abstract int returnAvailableBookCount(String iSBN);
	public abstract boolean allTheBooksAreAvailable(String iSBN);
	public abstract boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias);
	public abstract RegLivros findRegLivrosForThis(String isbn);
	public abstract void deleteThisRegLivros(RegLivros reg);
	public abstract void addDisponivel(String isbn);
	public abstract void remDisponivel(String isbn, int quant);
	public abstract boolean remRetirado(String isbn);
	public abstract boolean addRetirado(String isbn);

	
}
