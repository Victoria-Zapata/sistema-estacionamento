package model;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente extends Pessoa{
	private String telefone;
	private boolean ativo;
	private List<Veiculo> veiculos = new ArrayList<>();
	
	public List<Veiculo> getVeiculos() {
	    return veiculos;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Cliente(String nome, String cpf, String telefone, boolean ativo) {
		super(nome, cpf);
		this.telefone = telefone;
		this.ativo = ativo;
	}
	
	public void adicionarVeiculo(Veiculo v) {
	    if (v != null) {
	        this.veiculos.add(v);
	        v.setProprietario(this);
	    }
	}

}
