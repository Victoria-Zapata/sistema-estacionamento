package controller;

import model.*;
import java.time.LocalDateTime;

public class EstacionamentoController {
    // Acesso à instância única do model (Singleton)
    private Estacionamento est = Estacionamento.getInstancia();

   
     // RF05: Registrar Entrada Aplica o bloqueio se estiver lotado e usa a Factory para criar o cliente.

    public String registrarEntrada(String nome, String cpf, String placa, String tipo) {
        // Regra de Negócio: Bloqueio de Estacionamento Lotado
        if (!est.temVaga()) {
            return "ALERTA: Estacionamento lotado. Entrada bloqueada.";
        }

        // 1. Pattern Factory: Instancia o tipo correto de cliente
        Cliente novoCliente = ClienteFactory.criarCliente(tipo, nome, cpf, "0000-0000");
        
        // Cria o veículo e vincula ao cliente
        Veiculo v = new Veiculo(placa, "Geral", "Preto", novoCliente);
        novoCliente.adicionarVeiculo(v);
        
        // 2. Criação do Registro de Acesso
        RegistroAcesso registro = new RegistroAcesso(LocalDateTime.now(), v);
        
        // Atualiza o Model
        est.adicionarCliente(novoCliente);
        est.adicionarRegistro(registro);
        est.ocuparVaga(); // Incrementa o contador de vagas ocupadas

        return "Entrada liberada para a placa: " + placa;
    }

     // Lógica de Pagamento e Liberação de Saída Usa o Strategy para calcular o valor e atualiza o RegistroAcesso.
    
    public String processarSaida(String placa, FormaPagamento forma) {
        // Busca o registro que não tem data de saída (ainda está no pátio)
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

        // Pattern Strategy: Escolhe a regra de cálculo baseada no tipo de cliente
        CalculoCobrancaStrategy estrategia;
        if (registro.getVeiculo().getProprietario() instanceof ClienteFixo) {
            estrategia = new CalculoFixoStrategy();
        } else {
            estrategia = new CalculoTemporarioStrategy();
        }

        //  Calcula o valor passando os minutos
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
