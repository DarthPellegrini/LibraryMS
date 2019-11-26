// default package
// Generated 26/11/2019 13:42:20 by Hibernate Tools 3.6.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Endereco generated by hbm2java
 */
public class Endereco implements java.io.Serializable {

	private int enderecoId;
	private String cidade;
	private String bairro;
	private String rua;
	private int numero;
	private Set<Cliente> clientes = new HashSet<Cliente>(0);

	public Endereco() {
	}

	public Endereco(int enderecoId, String cidade, String bairro, String rua, int numero) {
		this.enderecoId = enderecoId;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
	}

	public Endereco(int enderecoId, String cidade, String bairro, String rua, int numero, Set<Cliente> clientes) {
		this.enderecoId = enderecoId;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.clientes = clientes;
	}

	public int getEnderecoId() {
		return this.enderecoId;
	}

	public void setEnderecoId(int enderecoId) {
		this.enderecoId = enderecoId;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Set<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

}
