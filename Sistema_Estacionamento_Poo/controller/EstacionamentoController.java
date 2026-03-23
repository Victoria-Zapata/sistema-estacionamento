package controller;

import model.*;
import java.time.LocalDateTime;

public class EstacionamentoController {
    // Acesso à instância única do model (Singleton)
    private Estacionamento est = Estacionamento.getInstancia();

     // RF05: Registrar Entrada Aplica o bloqueio se estiver lotado.

    public String registrarEntrada(String nome, String cpf, String placa, String tipo) {
        // Regra de Negócio: Bloqueio de Estacionamento Lotado
        if (!est.temVaga()) {
            return "ALERTA: Estacionamento lotado. Entrada bloqueada.";
        }

        // 1. Pattern Factory: Instancia o tipo correto de cliente
        Cliente novoCliente = ClienteFactory.criarCliente(tipo, nome, cpf, "0000-0000");
        Veiculo v = new Veiculo(placa, "Geral", "Preto", novoCliente);
        
        // 2. Criação do Registro
        RegistroAcesso registro = new RegistroAcesso(LocalDateTime.now(), v);
        
        // Atualiza o Model
        est.adicionarCliente(novoCliente);
        est.adicionarRegistro(registro);
        est.ocuparVaga(); // Incrementa vagasOcupadas

        return "Entrada liberada para a placa: " + placa;
    }

 // Lógica de Pagamento e Liberação de Saída

    public String processarSaida(String placa, FormaPagamento forma) {
        // Busca o registro que não tem data de saída (ainda está no pátio)
        RegistroAcesso registro = est.getRegistros().stream()
                .filter(r -> r.getVeiculo().getPlaca().equals(placa) && r.getDataHoraSaida() == null)
                .findFirst()
                .orElse(null);

        if (registro == null) {
            return "Erro: Veículo não encontrado ou já saiu.";
        }

        // Define a data de saída agora
        registro.setDataHoraSaida(LocalDateTime.now());

        // 3. Pattern Strategy: Escolhe a regra de cálculo
        CalculoCobrancaStrategy estrategia;
        if (registro.getVeiculo().getProprietario() instanceof ClienteFixo) {
            estrategia = new CalculoFixoStrategy();
        } else {
            estrategia = new CalculoTemporarioStrategy();
        }

        double valorTotal = estrategia.calcular(registro.getDataHoraEntrada(), registro.getDataHoraSaida());

        // 4. Lógica de Pagamento
        if (valorTotal > 0) {
            Pagamento pag = new Pagamento(forma, valorTotal, LocalDateTime.now(), registro.getVeiculo().getProprietario());
            est.adicionarPagamento(pag);
        }

        // Liberação da vaga
        est.liberarVaga();
        
        return String.format("Saída liberada! Placa: %s | Valor cobrado: R$ %.2f", placa, valorTotal);
    }
}
