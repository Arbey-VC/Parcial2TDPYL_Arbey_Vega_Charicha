package factory;

import modelo.Aereo;
import modelo.Envio;

public class AereoFactory extends EnvioFactory {

    @Override
    public String getNombreTipo() {
        return Aereo.TIPO;
    }

    @Override
    public Envio crearEnvio(String codigo, String cliente, double peso, double distancia) {
        return new Aereo(codigo, cliente, peso, distancia);
    }
}
