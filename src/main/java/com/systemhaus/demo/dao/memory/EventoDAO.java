package com.systemhaus.demo.dao.memory;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.EventoRepository;
import com.systemhaus.demo.domain.Livro;

public class EventoDAO extends EventoRepository{

	Biblioteca biblioteca;
	
	public EventoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	@Override
	public void save(Livro livro, Cartao cartao, String key) {
		biblioteca.addEvento(new Evento(livro, cartao, biblioteca.getTipoEvento(key)));
	}

}
