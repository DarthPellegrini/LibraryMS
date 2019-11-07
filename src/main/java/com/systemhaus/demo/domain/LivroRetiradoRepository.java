package com.systemhaus.demo.domain;

public abstract class LivroRetiradoRepository {
	
	public abstract boolean save(Livro livro, Cartao cartao, String key);

	public abstract int estenderRetirada(LivroRetirado livroRetirado, String key);
	
	public abstract LivroRetirado findLivroRetirado(Livro livro, Cartao cartao);
}
