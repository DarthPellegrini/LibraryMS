package com.systemhaus.demo.dao.memory;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;

public class LivroDAO implements LivroRepository {
	
	private Biblioteca biblioteca;

	public LivroDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	@Override
	public void save(Livro livro) {
		biblioteca.addDisponivel(livro.getISBN());
	}

	@Override
	public Livro findByExample(Livro example) {
		return biblioteca.getEstantes().stream()
			.flatMap(e -> e.getPrateleiras().stream())
			.flatMap(p -> p.getLivros().stream())
			.filter(l -> {
					return (l.getISBN().equals(example.getISBN()) || example.getISBN().isEmpty()) 
							&& (l.getEdicao() == example.getEdicao() || example.getEdicao() == 0) 
							&& (l.getTitulo().equals(example.getTitulo()) || example.getTitulo().isEmpty()) 
							&& (l.getAutor().equals(example.getAutor()) || example.getAutor().isEmpty()) 
							&& (l.getEditora().equals(example.getEditora()) || example.getEditora().isEmpty()) 
							&& (l.getNumeroPaginas() == example.getNumeroPaginas() || example.getNumeroPaginas() == 0);
			})
			.findFirst().orElse(null);
	}

}
