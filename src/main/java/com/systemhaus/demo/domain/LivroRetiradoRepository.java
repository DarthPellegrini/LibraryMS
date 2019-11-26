package com.systemhaus.demo.domain;

import java.util.List;

public abstract class LivroRetiradoRepository {
	
	public abstract boolean save(Livro livro, Cartao cartao, int pos);

	public abstract void estenderRetirada(LivroRetirado livroRetirado, int pos);
	
	public abstract LivroRetirado findLivroRetirado(Livro livro, Cartao cartao);

	public abstract int devolver(LivroRetirado livroRetirado, int pos);

	public abstract List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cartao cartao);

}
