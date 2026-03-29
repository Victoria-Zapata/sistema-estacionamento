package model;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
	//instancia unica
	private static Estacionamento instancia;

	private int capacidadeTotal;
	private int vagasOcupadas;
	private double valorHoraRotativo;
	
	// Listas para gerenciar o sistema
	private List<Pagamento> pagamentos = new ArrayList<>();
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<RegistroAcesso> registros;
    
    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
    
	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<RegistroAcesso> getRegistros() {
		return registros;
	}
	
	public int getCapacidadeTotal() {
		return capacidadeTotal;
	}
	
	public void setCapacidadeTotal(int capacidadeTotal) {
		this.capacidadeTotal = capacidadeTotal;
	}
	
	public int getVagasOcupadas() {
	    return vagasOcupadas;
	}

	public double getValorHoraRotativo() {
		return valorHoraRotativo;
	}

	public void setValorHoraRotativo(double valorHoraRotativo) {
		this.valorHoraRotativo = valorHoraRotativo;
	}

	//construtor privado
	private Estacionamento(int capacidadeTotal, double valorHoraRotativo) {
		this.capacidadeTotal = capacidadeTotal;
		this.valorHoraRotativo = valorHoraRotativo;
		// Inicializamos as listas para evitar NullPointerException
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.registros = new ArrayList<>();
	}

	//metodo para acessar a unica instancia
	public static Estacionamento getInstancia() {
	    if (instancia == null) {
	        instancia = new Estacionamento(50, 4.0); // default
	    }
	    return instancia;
	}
	
	public boolean temVaga() {
	    return vagasOcupadas < capacidadeTotal;
	}

	public void ocuparVaga() {
	    vagasOcupadas++;
	}

	public void liberarVaga() {
	    vagasOcupadas--;
	}
	
	//metodos de gerenciamento

    public void adicionarFuncionario(Funcionario f) {
        this.funcionarios.add(f);
    }

    public void adicionarCliente(Cliente c) {
        this.clientes.add(c);
    }

    public void adicionarRegistro(RegistroAcesso r) {
        this.registros.add(r);
    }
    
    public void adicionarPagamento(Pagamento p) {
        pagamentos.add(p);
    }

    
	
}