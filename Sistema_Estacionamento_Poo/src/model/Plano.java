package model;

public class Plano {
	private String descricao;
	private double valor;
	private int duracaoEmDias;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getDuracaoEmDias() {
		return duracaoEmDias;
	}
	public void setDuracaoEmDias(int duracaoEmDias) {
		this.duracaoEmDias = duracaoEmDias;
	}
	
	public Plano(String descricao, double valor, int duracaoEmDias) {
		this.descricao = descricao;
		this.valor = valor;
		this.duracaoEmDias = duracaoEmDias;
	}
	
	

}
