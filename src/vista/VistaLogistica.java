package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Envio;

public class VistaLogistica extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String[] COLUMNAS = { "Tipo", "Código", "Cliente", "Peso (kg)", "Distancia (km)", "Tarifa" };

    private final JButton btnAgregar = new JButton("Agregar envío");
    private final JButton btnRetirar = new JButton("Retirar envío");

    private final JTextField txtCodigo = new JTextField(12);
    private final JComboBox<String> cmbTipo = new JComboBox<>();
    private final JTextField txtCliente = new JTextField(12);
    private final JTextField txtDistancia = new JTextField(12);
    private final JTextField txtPeso = new JTextField(12);
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final DefaultTableModel modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final NumberFormat formatoMoneda = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CO"));

    public VistaLogistica() {
        super("Operador Logístico");
        formatoMoneda.setMaximumFractionDigits(2);
        construirInterfaz();
        habilitarFormulario(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 480);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barra.add(btnAgregar);
        barra.add(btnRetirar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(barra, BorderLayout.NORTH);
        norte.add(construirFormulario(), BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 8, 4, 8);
        c.anchor = GridBagConstraints.WEST;

        agregarCampo(panel, c, 0, 0, "Código", txtCodigo);
        agregarCampo(panel, c, 0, 2, "Tipo", cmbTipo);
        agregarCampo(panel, c, 1, 0, "Cliente", txtCliente);
        agregarCampo(panel, c, 1, 2, "Distancia en Km", txtDistancia);
        agregarCampo(panel, c, 2, 0, "Peso en Kg", txtPeso);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.add(btnGuardar);
        botones.add(btnCancelar);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(botones, c);
        return panel;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints c, int fila, int columna,
            String etiqueta, java.awt.Component campo) {
        c.gridwidth = 1;
        c.gridy = fila;
        c.gridx = columna;
        panel.add(new JLabel(etiqueta), c);
        c.gridx = columna + 1;
        panel.add(campo, c);
    }

    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addRetirarListener(ActionListener listener) {
        btnRetirar.addActionListener(listener);
    }

    public void addGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public String getCodigo() {
        return txtCodigo.getText();
    }

    public String getTipoSeleccionado() {
        return (String) cmbTipo.getSelectedItem();
    }

    public String getCliente() {
        return txtCliente.getText();
    }

    public String getPeso() {
        return txtPeso.getText();
    }

    public String getDistancia() {
        return txtDistancia.getText();
    }

    public void setTipos(Collection<String> tipos) {
        cmbTipo.removeAllItems();
        for (String tipo : tipos) {
            cmbTipo.addItem(tipo);
        }
    }

    public void habilitarFormulario(boolean habilitado) {
        txtCodigo.setEnabled(habilitado);
        cmbTipo.setEnabled(habilitado);
        txtCliente.setEnabled(habilitado);
        txtDistancia.setEnabled(habilitado);
        txtPeso.setEnabled(habilitado);
        btnGuardar.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
        if (habilitado) {
            txtCodigo.requestFocusInWindow();
        }
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtCliente.setText("");
        txtDistancia.setText("");
        txtPeso.setText("");
        if (cmbTipo.getItemCount() > 0) {
            cmbTipo.setSelectedIndex(0);
        }
    }

    public void mostrarEnvios(List<Envio> envios) {
        modeloTabla.setRowCount(0);
        for (Envio envio : envios) {
            modeloTabla.addRow(new Object[] {
                    envio.getTipo(),
                    envio.getCodigo(),
                    envio.getCliente(),
                    envio.getPeso(),
                    envio.getDistancia(),
                    "$ " + formatoMoneda.format(envio.calcularTarifa())
            });
        }
    }

    public String solicitarCodigo() {
        return JOptionPane.showInputDialog(this, "Código del envío a retirar:",
                "Retirar envío", JOptionPane.QUESTION_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
