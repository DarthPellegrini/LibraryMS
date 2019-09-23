package com.systemhaus.demo.domain;

public interface EstanteRepository {
	
	public Prateleira getPrateleiraWithEmptySpace();
	public void addEstante();

}
