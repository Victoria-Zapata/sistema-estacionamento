package model;

public class Veiculo {
	private String placa;
	private String modelo;
	private String cor;
	private Cliente proprietario;
	
	public Cliente getProprietario() {
		return proprietario;
	}
	public void setProprietario(Cliente proprietario) {
		this.proprietario = proprietario;
	}
	public String getPlaca() {
		return placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public Veiculo(String placa, String modelo, String cor, Cliente proprietario) {
		this.placa = placa;
		this.modelo = modelo;
		this.cor = cor;
		this.proprietario = proprietario;
	}
	
	

}
