package model;
import java.time.LocalDate;

public class ClienteFactory {
    public static Cliente criarCliente(String tipo, String nome, String cpf, String telefone) {
        if (tipo.equalsIgnoreCase("FIXO")) {
            // Valores default para um novo mensalista que o Controller pode ajustar depois
            return new ClienteFixo(nome, cpf, telefone, true, LocalDate.now().plusMonths(1), 
                                    StatusContrato.ATIVO, new Plano("Mensal", 200.0, 30));
        } else {
            return new ClienteTemporario(nome, cpf, telefone, true);
        }
    }
}
