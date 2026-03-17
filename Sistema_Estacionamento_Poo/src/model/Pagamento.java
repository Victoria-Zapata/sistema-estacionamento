package model;
import java.time.LocalDateTime;

public class Pagamento {
	private FormaPagamento formaPagamento;
	private double valorTotal;
	private LocalDateTime dataHora;
	
	private Cliente pagador;
	
	public Cliente getPagador() {
		return pagador;
	}
	public void setPagador(Cliente pagador) {
		this.pagador = pagador;
	}
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	public Pagamento(FormaPagamento formaPagamento, double valorTotal, LocalDateTime dataHora, Cliente pagador) {
		this.formaPagamento = formaPagamento;
		this.valorTotal = valorTotal;
		this.dataHora = dataHora;
		this.pagador = pagador;
	}
	
	
	

}
