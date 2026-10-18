package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.EnvioFactory;
import factory.RegistroFabricas;
import modelo.Envio;
import servicio.ServicioEnvios;
import vista.VistaLogistica;

public class ControladorLogistica {

    private final VistaLogistica vista;
    private final ServicioEnvios servicio;
    private final RegistroFabricas fabricas;

    public ControladorLogistica(VistaLogistica vista, ServicioEnvios servicio, RegistroFabricas fabricas) {
        this.vista = vista;
        this.servicio = servicio;
        this.fabricas = fabricas;

        vista.setTipos(fabricas.getTipos());
        vista.addAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                iniciarAgregado();
            }
        });
        vista.addGuardarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                guardarEnvio();
            }
        });
        vista.addCancelarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                cancelarAgregado();
            }
        });
        vista.addRetirarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                retirarEnvio();
            }
        });
        refrescarLista();
    }

    private void iniciarAgregado() {
        vista.limpiarFormulario();
        vista.habilitarFormulario(true);
    }

    private void cancelarAgregado() {
        vista.limpiarFormulario();
        vista.habilitarFormulario(false);
    }

    private void guardarEnvio() {
        try {
            String codigo = textoObligatorio(vista.getCodigo(), "El código");
            String cliente = textoObligatorio(vista.getCliente(), "El cliente");
            double peso = numeroPositivo(vista.getPeso(), "El peso");
            double distancia = numeroPositivo(vista.getDistancia(), "La distancia");

            EnvioFactory fabrica = fabricas.obtener(vista.getTipoSeleccionado());
            Envio envio = fabrica.crearEnvio(codigo, cliente, peso, distancia);
            servicio.agregar(envio);

            refrescarLista();
            cancelarAgregado();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void retirarEnvio() {
        String codigo = vista.solicitarCodigo();
        if (codigo == null) {
            return;
        }
        if (servicio.retirar(codigo.trim())) {
            refrescarLista();
            vista.mostrarMensaje("Envío " + codigo.trim() + " retirado.");
        } else {
            vista.mostrarMensaje("El código " + codigo.trim() + " no existe.");
        }
    }

    private void refrescarLista() {
        vista.mostrarEnvios(servicio.listar());
    }

    private String textoObligatorio(String texto, String campo) {
        String limpio = texto == null ? "" : texto.trim();
        if (limpio.isEmpty()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return limpio;
    }

    private double numeroPositivo(String texto, String campo) {
        String limpio = textoObligatorio(texto, campo).replace(',', '.');
        double valor;
        try {
            valor = Double.parseDouble(limpio);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(campo + " debe ser un número.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser mayor que cero.");
        }
        return valor;
    }
}
