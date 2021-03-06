package com.systemhaus.demo.domain;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jgoodies.binding.beans.Model;

public class LivroRetirado extends Model{

	/*
	 * Serial e Bindings
	 */
	private static final long serialVersionUID = 1L;
	//private static final String PROPERTY_LIVRO = "livro";
	
	private int id;
	private Livro livro; //referência ao livro
	private Cliente cliente; //referência ao cliente
	private Evento retirada; //evento de retirada do livro
	private List<Evento> renovacoes; //lista de renovações
	private Evento devolucao; //evento de devolução do livro
	private Date dataDevolucao; //data em que a devolução deve ser feita
	
	public LivroRetirado() {
		this.setLivro(new Livro());
		this.setCliente(new Cliente());
		this.setRetirada(null);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		this.dataDevolucao = null;
	}
	
	public LivroRetirado(Livro livro, Cliente cliente) {
		this.setLivro(livro);
		this.setCliente(cliente);
		this.setRetirada(null);
		this.setRenovacoes(new ArrayList<Evento>());
		this.setDevolucao(null);
		this.dataDevolucao = null;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Evento getRetirada() {
		return retirada;
	}
	public void setRetirada(Evento retirada) {
		this.retirada = retirada;
	}
	public void addRetirada(Evento retirada) {
		this.setRetirada(retirada);
		this.setDataDevolucao(Date.from(
						Instant.ofEpochMilli(
						retirada.getData().getTime())
						.atZone(ZoneId.systemDefault())
						.toLocalDateTime().plusDays(7)
						.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	public LocalDate getDataRetiradaAsLocalDate() {
		if (retirada == null)
			return null;
		else
			return retirada.getDataRaw().toLocalDate();	
	}
	public List<Evento> getRenovacoes() {
		return renovacoes;
	}

	public void setRenovacoes(List<Evento> renovacoes) {
		this.renovacoes = renovacoes;
	}
	public int getTotalRenovacoes() {
		return null == this.getRenovacoes() ? 0 : getRenovacoes().size();
	}
	
	public Evento getLastRenovacao() {
		return null == this.getRenovacoes() ? null : getRenovacoes().isEmpty() ? null : getRenovacoes().get(getRenovacoes().size()-1);
	}
	
	public Evento getDevolucao() {
		return devolucao;
	}
	
	public LocalDate getDataDevolucaoAsLocalDate() {
		return Instant.ofEpochMilli(this.getDataDevolucao().getTime())
		        .atZone(ZoneId.systemDefault())
		        .toLocalDateTime().toLocalDate();	
	}
	
	public Date getDataDevolucao() {
		return this.dataDevolucao;
	}
	
	public LocalDate getDataDevolucaoRealAsLocalDate() {
		if (dataDevolucao == null)
			return null;
		else 
			return Instant.ofEpochMilli(dataDevolucao.getTime())
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
	}
	
	public void setDevolucao(Evento evento) {
		this.devolucao = evento;
	}
	
	public void estenderRetirada(Evento evento) {
		this.renovacoes.add(evento.copy());
		this.setDataDevolucao(evento.getData());
	}
	
	public void setDataDevolucao(Date date) {
		this.dataDevolucao = date;
	}

	/*
	 * Essa parte abaixo deveria estar em uma VIEW, mas como é a noite anterior a data de entrega
	 * vou cometer esse pecado e fazer tudo no domain
	 * NÃO SE FAZ ISSO EM UM SOFTWARE DE VERDADE, ISSO É SÓ UM PROJETO DE TESTE
	 */
	
	public String getDataDevolucaoEsperada() {
		return  new SimpleDateFormat("dd/MM/yy").format(this.getDataDevolucao());
	}
	
	public String getDataDevolucaoReal() {
		return null != this.getDevolucao() ? new SimpleDateFormat("dd/MM/yy").format(this.getDevolucao().getData()) : "";
	}
	
	public String getDataLastRenovacao() {
		return  null != this.getLastRenovacao() ? new SimpleDateFormat("dd/MM/yy").format(this.getLastRenovacao().getData()) : "";
	}
	
	public String getDataRetirada() {
		return null != this.getRetirada() ? new SimpleDateFormat("dd/MM/yy").format(this.getRetirada().getData()) : "";
	}
	
	public String getNome(){
		return this.getCliente().getNome();
	}
	
	public String getCPF(){
		return this.getCliente().getCpf();
	}
	
	public String getISBN(){
		return this.getLivro().getISBN();
	}
	
	public String getTitulo(){
		return this.getLivro().getTitulo();
	}
	
}
