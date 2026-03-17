package model;
import java.time.LocalDate;

public class ClienteFixo extends Cliente{
	private LocalDate dataVencimento;
	private StatusContrato statusContrato;
	private Plano plano;
	
	public Plano getPlano() {
		return plano;
	}
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public StatusContrato getStatusContrato() {
		return statusContrato;
	}
	public void setStatusContrato(StatusContrato statusContrato) {
		this.statusContrato = statusContrato;
	}
	
	public ClienteFixo(String nome, String cpf, String telefone, boolean ativo, LocalDate dataVencimento,
			StatusContrato statusContrato, Plano plano) {
		super(nome, cpf, telefone, ativo);
		this.dataVencimento = dataVencimento;
		this.statusContrato = statusContrato;
		this.plano = plano;
	}
	
	

}
