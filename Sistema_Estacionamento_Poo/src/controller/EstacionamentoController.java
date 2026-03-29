package controller;

import model.*;
import java.time.LocalDateTime;

public class EstacionamentoController {
	
    //acesso a instância única do model (singleton)
    private Estacionamento est = Estacionamento.getInstancia();

    public String registrarEntrada(String nome, String cpf, String placa, String tipo) {
    	
        //bloqueio de Estacionamento Lotado
        if (!est.temVaga()) {
            return "ALERTA: Estacionamento lotado. Entrada bloqueada.";
        }

        //factory instancia o tipo correto de cliente
        Cliente novoCliente = ClienteFactory.criarCliente(tipo, nome, cpf, "0000-0000");
        
        //cria o veículo e vincula ao cliente
        Veiculo v = new Veiculo(placa, "Geral", "Preto", novoCliente);
        novoCliente.adicionarVeiculo(v);
        
        //criação do Registro de Acesso
        RegistroAcesso registro = new RegistroAcesso(LocalDateTime.now(), v);
        
        //salva tudo no estacionamento(singleton)
        est.adicionarCliente(novoCliente);
        est.adicionarRegistro(registro);
        est.ocuparVaga();

        return "Entrada liberada para a placa: " + placa;
    }

     // Lógica de Pagamento e Liberação de Saída Usa o Strategy para calcular o valor e atualiza o RegistroAcesso.
    
    public String processarSaida(String placa, FormaPagamento forma) {
    	//prucura na lista de registros um registro que tenha essa placa e ainda nao saiu
        RegistroAcesso registro = est.getRegistros().stream()
                .filter(r -> r.getVeiculo().getPlaca().equals(placa) && r.getDataHoraSaida() == null)
                .findFirst()
                .orElse(null);

        if (registro == null) {
            return "Erro: Veículo não encontrado no pátio ou já saiu.";
        }

        //  Define a data de saída como o momento atual
        registro.setDataHoraSaida(LocalDateTime.now());

        // Usa o método calcularTempo() da classe RegistroAcesso
        long tempoPermanencia = registro.calcularTempo();

        //strategy: Escolhe a regra de cálculo baseada no tipo de cliente
        CalculoCobrancaStrategy estrategia;
        if (registro.getVeiculo().getProprietario() instanceof ClienteFixo) {
            estrategia = new CalculoFixoStrategy();
        } else {
            estrategia = new CalculoTemporarioStrategy();
        }

        //  Calcula o valor sem saber qual tipo de cliente
        double valorTotal = estrategia.calcular(tempoPermanencia);

        // Lógica de Pagamento
        if (valorTotal > 0) {
            Pagamento pag = new Pagamento(forma, valorTotal, LocalDateTime.now(), registro.getVeiculo().getProprietario());
            est.adicionarPagamento(pag);
            
            // Vincula o pagamento ao registro usando o método setPagamento()
            registro.setPagamento(pag);
        }

        //  Liberação da vaga
        est.liberarVaga();
        
        return String.format("Saída liberada! Placa: %s | Tempo: %d min | Valor cobrado: R$ %.2f", 
                              placa, tempoPermanencia, valorTotal);
    }
}
