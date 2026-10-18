package factory;

import modelo.Envio;

public abstract class EnvioFactory {

    public abstract String getNombreTipo();

    public abstract Envio crearEnvio(String codigo, String cliente, double peso, double distancia);
}
