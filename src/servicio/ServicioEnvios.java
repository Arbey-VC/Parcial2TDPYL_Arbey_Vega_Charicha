package servicio;

import java.util.List;
import modelo.Envio;

public interface ServicioEnvios {

    void agregar(Envio envio);

    boolean retirar(String codigo);

    List<Envio> listar();
}
