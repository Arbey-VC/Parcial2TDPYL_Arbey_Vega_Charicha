package factory;

import modelo.Envio;
import modelo.Maritimo;

public class MaritimoFactory extends EnvioFactory {

    @Override
    public String getNombreTipo() {
        return Maritimo.TIPO;
    }

    @Override
    public Envio crearEnvio(String codigo, String cliente, double peso, double distancia) {
        return new Maritimo(codigo, cliente, peso, distancia);
    }
}
