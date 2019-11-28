package com.systemhaus.demo.domain;

import java.util.List;

public abstract class LivroRetiradoRepository {
	
	public abstract boolean save(Livro livro, Cliente cliente);

	public abstract void estenderRetirada(LivroRetirado livroRetirado);
	
	public abstract LivroRetirado findLivroRetirado(Livro livro, Cliente cliente);

	public abstract int devolver(LivroRetirado livroRetirado);

	public abstract List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente);

}
