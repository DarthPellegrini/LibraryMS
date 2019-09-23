package com.systemhaus.demo.domain;

public interface LivroRepository {
	
	public void save(Livro livro);
	public Livro findByExample(Livro livro);

}
