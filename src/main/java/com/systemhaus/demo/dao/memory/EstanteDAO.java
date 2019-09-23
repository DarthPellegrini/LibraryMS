package com.systemhaus.demo.dao.memory;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.EstanteRepository;

public class EstanteDAO implements EstanteRepository {
	
	private Biblioteca biblioteca;

	public EstanteDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	@Override
	public Prateleira getPrateleiraWithEmptySpace() {
		return biblioteca.getPrateleiraWithEmptySpace();
	}

	@Override
	public void addEstante() {
		biblioteca.addEstante();
	}

}
