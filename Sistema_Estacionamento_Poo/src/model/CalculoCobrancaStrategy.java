package model;
import java.time.Duration;
import java.time.LocalDateTime;

public interface CalculoCobrancaStrategy {
    double calcular(LocalDateTime entrada, LocalDateTime saida);
}

public class CalculoTemporarioStrategy implements CalculoCobrancaStrategy {
    @Override
    public double calcular(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        double valorHora = Estacionamento.getInstancia().getValorHoraRotativo();
        return Math.ceil(minutos / 60.0) * valorHora;
    }
}

public class CalculoFixoStrategy implements CalculoCobrancaStrategy {
    @Override
    public double calcular(LocalDateTime entrada, LocalDateTime saida) {
        return 0.0; // Já pago via plano mensal
    }
}
