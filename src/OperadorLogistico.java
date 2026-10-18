import javax.swing.SwingUtilities;
import controlador.ControladorLogistica;
import factory.AereoFactory;
import factory.MaritimoFactory;
import factory.RegistroFabricas;
import factory.TerrestreFactory;
import servicio.ServicioEnvios;
import servicio.ServicioEnviosImpl;
import vista.VistaLogistica;

public class OperadorLogistico {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegistroFabricas fabricas = new RegistroFabricas();
                fabricas.registrar(new TerrestreFactory());
                fabricas.registrar(new AereoFactory());
                fabricas.registrar(new MaritimoFactory());

                ServicioEnvios servicio = new ServicioEnviosImpl();
                VistaLogistica vista = new VistaLogistica();
                new ControladorLogistica(vista, servicio, fabricas);
                vista.setVisible(true);
            }
        });
    }
}
