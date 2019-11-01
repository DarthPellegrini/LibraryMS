package com.systemhaus.demo.domain;

public abstract class EventoRepository {
	
	public abstract void save(Livro livro, Cartao cartao, String key);
}
