package model;
import java.time.LocalDateTime;

public class RegistroAcesso {
	private LocalDateTime dataHoraEntrada;
	private LocalDateTime dataHoraSaida;
	private Veiculo veiculo;
	private Pagamento pagamento;
	
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public LocalDateTime getDataHoraEntrada() {
		return dataHoraEntrada;
	}
	public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}
	public LocalDateTime getDataHoraSaida() {
		return dataHoraSaida;
	}
	
	public RegistroAcesso(LocalDateTime dataHoraEntrada, Veiculo veiculo) {
		this.dataHoraEntrada = dataHoraEntrada;
		this.veiculo = veiculo;
	}
	
	public long calcularTempo() {
	    return java.time.Duration.between(dataHoraEntrada, dataHoraSaida).toMinutes();
	}

}
