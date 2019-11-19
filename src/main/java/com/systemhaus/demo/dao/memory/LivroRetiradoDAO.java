package com.systemhaus.demo.dao.memory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoDAO extends LivroRetiradoRepository{

	Biblioteca biblioteca;
	
	public LivroRetiradoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	@Override
	public boolean save(Livro livro, Cartao cartao, String key) {
		if (biblioteca.addRetirado(livro.getISBN())) {
			
			biblioteca.addLivroRetirado(new LivroRetirado(livro, cartao, new Evento(biblioteca.getTipoEvento(key))));
			return true;
		}else
			return false;
		
	}
	
	@Override
	public int estenderRetirada(LivroRetirado livroRetirado, String key) {
		if (livroRetirado.getTotalRenovacoes() < 3) {
			LocalDateTime dataRetirado = LocalDateTime.now();
			if(livroRetirado.getTotalRenovacoes() == 0)
				dataRetirado = livroRetirado.getRetirada().getDataRaw();
			else
				dataRetirado = livroRetirado.getLastRenovacao().getDataRaw();
			if(dataRetirado.plusDays(2).isAfter(LocalDateTime.now())) {
				livroRetirado.estenderRetirada(new Evento(biblioteca.getTipoEvento(key)));
				return 0; //sucesso
			}else return 2; //erro 2- renovação muito cedo
		}else return 1; //erro 1- limite de renovações ultrapassado
	}
	
	@Override
	public LivroRetirado findLivroRetirado(Livro livro, Cartao cartao) {
		for(ListIterator<LivroRetirado> lrIt = biblioteca.getLivrosRetirados().listIterator(biblioteca.getLivrosRetirados().size());
				lrIt.hasPrevious();) {
			LivroRetirado livroRetirado = lrIt.previous();
			if (livroRetirado.getLivro().isEqual(livro) && livroRetirado.getCartao().isEqual(cartao))
				return livroRetirado;
		}
		return null;
	}
	
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cartao cartao) {
		List<LivroRetirado> livrosRetirados = new ArrayList<>();
		for (LivroRetirado livroRetirado : biblioteca.getLivrosRetirados()) {
			if (livroRetirado.getDevolucao() == null &&
				((livroRetirado.getLivro().isEqual(livro) && livroRetirado.getCartao().isEqual(cartao)) ||
				(livroRetirado.getLivro().isEqual(livro) && !cartao.validate()) || 
				(livroRetirado.getCartao().isEqual(cartao) && !livro.validate())))
				livrosRetirados.add(livroRetirado);
		}
		return livrosRetirados;
	}
	
	@Override
	public int devolver(LivroRetirado livroRetirado, String key) {
		biblioteca.remRetirado(livroRetirado.getLivro().getISBN());
		livroRetirado.getLivro().setRetirado(false);
		livroRetirado.devolver(new Evento(biblioteca.getTipoEvento(key)));
		return 0;
	}
	
	
}
