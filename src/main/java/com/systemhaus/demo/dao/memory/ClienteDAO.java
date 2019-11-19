package com.systemhaus.demo.dao.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;

public class ClienteDAO implements ClienteRepository {

	Biblioteca biblioteca;
	
	public ClienteDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	@Override
	public void save(Cliente cliente) {
		biblioteca.addCliente(cliente);
	}
	
	@Override
	public List<Cliente> findSimilarClients(Cliente similar){
		List<Cliente> clientes = new ArrayList<>();
		String[] dadosSimilares = {
				similar.getNome().isEmpty() ? "" : similar.getNome().toLowerCase(),
				similar.getCidade().isEmpty() ? "" : similar.getCidade().toLowerCase(),
				similar.getBairro().isEmpty() ? "" : similar.getBairro().toLowerCase(),
				similar.getRua().isEmpty() ? "" : similar.getRua().toLowerCase()};
		if(similar.getNome().isEmpty() && similar.getCPF().isEmpty() 
			&& similar.getTelefone().isEmpty() && similar.getCidade().isEmpty() 
			&& similar.getBairro().isEmpty() && similar.getRua().isEmpty()
			&& similar.getNumero() == 0)
			return clientes;
		for(ListIterator<Cliente> cIt = biblioteca.getClientes().listIterator(); cIt.hasNext();) {
			Cliente c = cIt.next();
			if((c.getCPF().equals(similar.getCPF()) || similar.getCPF().isEmpty())
				&& (c.getTelefone().equals(similar.getTelefone()) || similar.getTelefone().isEmpty())
				&& (c.getNumero() == similar.getNumero() || similar.getNumero() == 0)
				&& (c.getNome().toLowerCase().contains(dadosSimilares[0])
						|| similar.getNome().isEmpty())
				&& (c.getCidade().toLowerCase().contains(dadosSimilares[1])
						|| similar.getCidade().isEmpty())
				&& (c.getBairro().toLowerCase().contains(dadosSimilares[2])
						|| similar.getBairro().isEmpty())
				&& (c.getRua().toLowerCase().contains(dadosSimilares[3])
						|| similar.getRua().isEmpty())) {
				clientes.add(c);
			}
		}
		return clientes;
	}
	
	@Override
	public void edit(String CPF, Cliente cliente) {
		for(ListIterator<Cliente> cIt = biblioteca.getClientes().listIterator(); cIt.hasNext();) {
			Cliente c = cIt.next();
			if(c.getCPF().equals(CPF)) c.setAllDataFrom(cliente);
		}
	}
	
	@Override
	public void delete(String CPF) {
		for(ListIterator<Cliente> cIt = biblioteca.getClientes().listIterator(); cIt.hasNext();) {
			Cliente c = cIt.next();
			if(c.getCPF().equals(CPF)) {
				cIt.remove();
				return;
			}
		}
	}
	
	@Override 
	public boolean thisCpfAlreadyExists(String CPF) {
		for(ListIterator<Cliente> cIt = biblioteca.getClientes().listIterator(); cIt.hasNext();) {
			Cliente c = cIt.next();
			if(c.getCPF().equals(CPF)) return true;
		}
		return false;
	}
	
	@Override
	public void createValidCode(Cliente c) {
		String code = "";
		do {
			code = c.getCartao().createNewValidCodCartao();
		}while(findCardWhitThisCode(code));
		c.setCodCartao(code);
	}
	
	@Override
	public boolean findCardWhitThisCode(String code) {
		return biblioteca.getClientes().stream()
		.anyMatch(f -> {return (f.getCodCartao().equals(code));});
	}
	
	@Override
	public Cliente findClientWithThisCardCode(String code) {
		return biblioteca.getClientes().stream()
		.filter(f -> (f.getCodCartao().equals(code)))
		.findFirst().orElse(null);
	}
}
