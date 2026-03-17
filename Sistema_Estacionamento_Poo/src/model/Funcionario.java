package model;

public class Funcionario extends Pessoa{
	private String login;
	private String senhaHash;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenhaHash() {
		return senhaHash;
	}
	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}
	
	public Funcionario(String nome, String cpf, String login, String senhaHash) {
		super(nome, cpf);
		this.login = login;
		this.senhaHash = senhaHash;
	}
	
}
