package com.systemhaus.demo.dao.memory;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Endereco;
import com.systemhaus.demo.domain.EnderecoRepository;

public class EnderecoDAO implements EnderecoRepository{

	Biblioteca biblioteca;
	
	public EnderecoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	/*
	 * Método que verifica se existem muitas pessoas no mesmo endereço
	 */
	@Override
	public boolean thereAreTooManySimilarAddresses(Endereco exemplo) {
		return biblioteca.getClientes().stream()
		.filter(e -> {return (e.getCidade().equals(exemplo.getCidade()) &&
								e.getBairro().equals(exemplo.getBairro()) &&
								e.getRua().equals(exemplo.getRua()) &&
								e.getNumero() == exemplo.getNumero());})
		.count() >= 6;
	}
	
}
