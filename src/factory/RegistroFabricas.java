package factory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RegistroFabricas {

    private final Map<String, EnvioFactory> fabricas = new LinkedHashMap<>();

    public void registrar(EnvioFactory fabrica) {
        fabricas.put(fabrica.getNombreTipo(), fabrica);
    }

    public EnvioFactory obtener(String tipo) {
        EnvioFactory fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalArgumentException("Tipo de envío no permitido: " + tipo);
        }
        return fabrica;
    }

    public List<String> getTipos() {
        return new ArrayList<>(fabricas.keySet());
    }
}
