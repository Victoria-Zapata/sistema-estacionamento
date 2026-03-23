package model;

public class CalculoTemporarioStrategy implements CalculoCobrancaStrategy {
    @Override
    public double calcular(long minutos) {
        double valorHora = Estacionamento.getInstancia().getValorHoraRotativo();
        return Math.ceil(minutos / 60.0) * valorHora;
    }
}

