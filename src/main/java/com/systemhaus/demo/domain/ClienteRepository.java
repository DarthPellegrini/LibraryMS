package com.systemhaus.demo.domain;

import java.util.List;

public interface ClienteRepository {

	public abstract void save(Cliente cliente);
	public abstract List<Cliente> findSimilarClients(Cliente cliente);
	public abstract void createValidCode(Cliente c);
	public abstract boolean findCardWithThisCode(String code);
	public abstract boolean thisCpfAlreadyExists(String CPF);
	public abstract Cliente findClientWithThisCardCode(String code);
	public abstract void update(Cliente cliente);
	public abstract void delete(Cliente cliente);
	public abstract boolean thereAreTooManySimilarAddresses(Endereco exemplo);
	@Deprecated
	public abstract void delete(String CPF);
	@Deprecated
	public abstract void edit(String CPF, Cliente cliente);
	
}
