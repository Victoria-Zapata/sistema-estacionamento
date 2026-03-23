package view;

import controller.EstacionamentoController;
import model.FormaPagamento;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EstacionamentoController controller = new EstacionamentoController();
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\nMenu do Estacionamento:");
            System.out.println("1 - Registrar Entrada");
            System.out.println("2 - Registrar Saida");
            System.out.println("3 - Sair");
            System.out.print("Opcao: ");
            
            opcao = sc.nextInt();
            sc.nextLine(); 

            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                
                System.out.print("Placa: ");
                String placa = sc.nextLine();
                
                System.out.print("Tipo (FIXO ou TEMP): ");
                String tipo = sc.nextLine();

                String retorno = controller.registrarEntrada(nome, cpf, placa, tipo);
                System.out.println(retorno);

            } else if (opcao == 2) {
                System.out.print("Placa: ");
                String placa = sc.nextLine();

                System.out.println("Pagamento: 1-PIX, 2-DINHEIRO, 3-CARTAO");
                int opPagamento = sc.nextInt();
                sc.nextLine();

                FormaPagamento forma = FormaPagamento.PIX; // default
                if (opPagamento == 2) {
                    forma = FormaPagamento.DINHEIRO;
                } else if (opPagamento == 3) {
                    forma = FormaPagamento.CARTAO;
                }

                String retorno = controller.processarSaida(placa, forma);
                System.out.println(retorno);
                
            } else if (opcao == 3) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opcao invalida.");
            }
        }
        
        sc.close();
    }
}
