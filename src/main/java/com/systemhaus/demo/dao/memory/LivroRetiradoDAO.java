package com.systemhaus.demo.dao.memory;

import java.time.LocalDateTime;

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

	
	//TODO: método de busca de livroRetirado e passar os dados de cliente/livro por parâmetro
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
		for (LivroRetirado livroRetirado : biblioteca.getLivrosRetirados()) {
			if (livroRetirado.getLivro().equals(livro) && livroRetirado.getCartao().equals(cartao))
				return livroRetirado;
		}
		return null;
	}
	
}
