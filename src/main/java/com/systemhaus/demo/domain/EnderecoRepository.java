package com.systemhaus.demo.domain;

public interface EnderecoRepository {

	public abstract boolean thereAreTooManySimilarAddresses(Endereco exemplo);
	
}
