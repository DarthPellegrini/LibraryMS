package com.systemhaus.demo.domain;

public interface EstanteRepository {
	
	public Prateleira getPrateleiraWithEmptySpace();
	public void addEstante();
	public int getCountOfEstantes();
	public boolean needsReorganization();
	public void organizeLibrary();
}
