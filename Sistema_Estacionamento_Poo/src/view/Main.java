package view;

import controller.EstacionamentoController;
import model.FormaPagamento;
import model.Estacionamento;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EstacionamentoController controller = new EstacionamentoController();
        int opcao = 0;

        while (opcao != 5) {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE ESTACIONAMENTO (MVC/SOLID)");
            System.out.println("========================================");
            System.out.println("1 - Registrar Entrada (Interativo)");
            System.out.println("2 - Registrar Saída (Interativo)");
            System.out.println("3 - EXECUTAR DEMO SOLID (Automático)");
            System.out.println("4 - Visualizar Status do Pátio");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            
            if (!sc.hasNextInt()) {
                sc.next();
                continue;
            }
            opcao = sc.nextInt();
            sc.nextLine(); 

            if (opcao == 1) {
                registrarEntradaInterativo(sc, controller);
            } else if (opcao == 2) {
                registrarSaidaInterativo(sc, controller);
            } else if (opcao == 3) {
                executarDemoSolid(controller);
            } else if (opcao == 4) {
                exibirStatusEstacionamento();
            } else if (opcao == 5) {
                System.out.println("Encerrando sistema...");
            } else {
                System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private static void exibirStatusEstacionamento() {
        Estacionamento est = Estacionamento.getInstancia();
        System.out.println("\n--- STATUS ATUAL DO PÁTIO (SINGLETON) ---");
        System.out.println("Capacidade Total: " + est.getCapacidadeTotal());
        System.out.println("Vagas Ocupadas:   " + est.getVagasOcupadas());
        System.out.println("Vagas Livres:     " + (est.getCapacidadeTotal() - est.getVagasOcupadas()));
        System.out.println("Status:           " + (est.temVaga() ? "DISPONÍVEL" : "LOTADO"));
        System.out.println("------------------------------------------");
    }

    private static void registrarEntradaInterativo(Scanner sc, EstacionamentoController controller) {
        System.out.print("Nome do Cliente: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Placa do Veículo: ");
        String placa = sc.nextLine();
        System.out.print("Tipo de Cliente (FIXO para Mensalista ou TEMP para Rotativo): ");
        String tipo = sc.nextLine();

        System.out.println("\n[Processando...] " + controller.registrarEntrada(nome, cpf, placa, tipo));
    }

    private static void registrarSaidaInterativo(Scanner sc, EstacionamentoController controller) {
        System.out.print("Digite a Placa para Saída: ");
        String placa = sc.nextLine();
        System.out.println("Forma de Pagamento: 1-PIX, 2-DINHEIRO, 3-CARTÃO");
        int op = sc.nextInt();
        sc.nextLine();

        FormaPagamento forma = (op == 2) ? FormaPagamento.DINHEIRO : (op == 3) ? FormaPagamento.CARTAO : FormaPagamento.PIX;
        System.out.println("\n[Processando...] " + controller.processarSaida(placa, forma));
    }

    private static void executarDemoSolid(EstacionamentoController controller) {
        System.out.println("\n--- INICIANDO DEMONSTRAÇÃO DOS PRINCÍPIOS SOLID & PATTERNS ---");
        
        // 1. Singleton
        System.out.println("\n[SINGLETON] Verificando capacidade do Estacionamento...");
        Estacionamento est = Estacionamento.getInstancia();
        System.out.println("Capacidade Atual: " + est.getCapacidadeTotal() + " vagas.");

        // 2. Factory & OCP
        System.out.println("\n[FACTORY] Criando dois tipos de clientes sem instanciar classes concretas diretamente...");
        controller.registrarEntrada("João Mensalista", "111.111.111-11", "ABC-1234", "FIXO");
        controller.registrarEntrada("Maria Rotativo", "222.222.222-22", "XYZ-9999", "TEMP");
        System.out.println("-> João (FIXO) e Maria (TEMP) registrados com sucesso.");

        // Simulação de tempo 
        System.out.println("\n[DEMO] Simulando passagem de 120 minutos para os dois veículos...");
        java.time.LocalDateTime duasHorasAtras = java.time.LocalDateTime.now().minusHours(2);
        for (model.RegistroAcesso r : est.getRegistros()) {
            if (r.getDataHoraSaida() == null) {
                r.setDataHoraEntrada(duasHorasAtras);
            }
        }

        // 3. SRP & Encapsulamento
        System.out.println("\n[SRP] Verificando ocupação do pátio via Controller...");
        System.out.println("Vagas ocupadas no momento: " + est.getVagasOcupadas());

        // 4. Strategy & LSP
        System.out.println("\n[STRATEGY] Processando saídas com regras de cálculo diferentes (Polimorfismo)...");
        System.out.println("Saída 1 (Mensalista): " + controller.processarSaida("ABC-1234", FormaPagamento.PIX));
        System.out.println("Saída 2 (Rotativo):   " + controller.processarSaida("XYZ-9999", FormaPagamento.CARTAO));
        
        System.out.println("\n[CONCLUÍDO] Observe que o sistema usou cálculos diferentes para cada cliente");
        System.out.println("sem nenhum 'if/else' de regras de negócio dentro do Controller!");
        System.out.println("--------------------------------------------------------------\n");
    }
}
