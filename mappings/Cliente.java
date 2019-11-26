// default package
// Generated 26/11/2019 13:42:20 by Hibernate Tools 3.6.0.Final

/**
 * Cliente generated by hbm2java
 */
public class Cliente implements java.io.Serializable {

	private String cpf;
	private Cartao cartao;
	private Endereco endereco;
	private String nome;
	private String telefone;

	public Cliente() {
	}

	public Cliente(String cpf, String nome, String telefone) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
	}

	public Cliente(String cpf, Cartao cartao, Endereco endereco, String nome, String telefone) {
		this.cpf = cpf;
		this.cartao = cartao;
		this.endereco = endereco;
		this.nome = nome;
		this.telefone = telefone;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Cartao getCartao() {
		return this.cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
