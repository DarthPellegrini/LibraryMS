package com.systemhaus.demo.domain;

import java.util.List;

public interface ClienteRepository {

	public abstract void save(Cliente cliente);
	public abstract List<Cliente> findSimilarClients(Cliente cliente);
	public abstract void createValidCode(Cliente c);
	public abstract boolean findCardWhitThisCode(String code);
	public abstract void delete(String CPF);
	public abstract void edit(String CPF, Cliente cliente);
	public abstract boolean thisCpfAlreadyExists(String CPF);

	
}
