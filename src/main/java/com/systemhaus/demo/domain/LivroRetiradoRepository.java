package com.systemhaus.demo.domain;

import java.util.List;

public abstract class LivroRetiradoRepository {
	
	public abstract boolean save(Livro livro, Cartao cartao, String key);

	public abstract void estenderRetirada(LivroRetirado livroRetirado, String key);
	
	public abstract LivroRetirado findLivroRetirado(Livro livro, Cartao cartao);

	public abstract int devolver(LivroRetirado livroRetirado, String key);

	public abstract List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cartao cartao);
}
