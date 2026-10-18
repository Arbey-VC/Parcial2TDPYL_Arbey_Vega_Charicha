package servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import modelo.Envio;

public class ServicioEnviosImpl implements ServicioEnvios {

    private final List<Envio> envios = new ArrayList<>();

    @Override
    public void agregar(Envio envio) {
        if (existe(envio.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un envío con el código " + envio.getCodigo());
        }
        envios.add(envio);
    }

    @Override
    public boolean retirar(String codigo) {
        Iterator<Envio> iterador = envios.iterator();
        while (iterador.hasNext()) {
            if (iterador.next().getCodigo().equals(codigo)) {
                iterador.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Envio> listar() {
        return Collections.unmodifiableList(envios);
    }

    private boolean existe(String codigo) {
        for (Envio envio : envios) {
            if (envio.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}
