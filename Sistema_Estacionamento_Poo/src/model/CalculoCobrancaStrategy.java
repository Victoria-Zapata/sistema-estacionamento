package model;

public interface CalculoCobrancaStrategy {
    double calcular(long minutos);
}

public class CalculoTemporarioStrategy implements CalculoCobrancaStrategy {
    @Override
    public double calcular(long minutos) {
        double valorHora = Estacionamento.getInstancia().getValorHoraRotativo();
        return Math.ceil(minutos / 60.0) * valorHora;
    }
}

public class CalculoFixoStrategy implements CalculoCobrancaStrategy {
    @Override
    public double calcular(long minutos) {
        return 0.0;
    }
}
