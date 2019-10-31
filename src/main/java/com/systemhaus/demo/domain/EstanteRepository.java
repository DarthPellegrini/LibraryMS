package com.systemhaus.demo.domain;

public interface EstanteRepository {
	
	public Prateleira getPrateleiraWithEmptySpace();
	public void addEstante();
	public int getCountOfEstantes();
	public int returnBookCount(String iSBN);
	public boolean allTheBooksAreAvailable(String iSBN);
	public boolean needsReorganization();
	public void organizeLibrary();
	public boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias);
	public int returnAvailableBookCount(String iSBN);
}
