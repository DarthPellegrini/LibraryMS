package com.systemhaus.demo.domain;

import java.util.List;

public interface LivroRetiradoRepository {
	
	public abstract boolean save(Livro livro, Cliente cliente);

	public abstract void estenderRetirada(LivroRetirado livroRetirado);

	public abstract int devolver(LivroRetirado livroRetirado);

	public abstract List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente);

}
