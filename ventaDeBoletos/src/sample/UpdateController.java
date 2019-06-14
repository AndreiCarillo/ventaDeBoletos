package sample;

import com.mysql.cj.xdevapi.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.security.auth.login.FailedLoginException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML private TextField textA;
    @FXML private TextField textB;
    @FXML private TextField textC;
    @FXML private TextField textD;
    @FXML private TextField textE;
    @FXML private TextField textF;
    @FXML private TextField textG;
    @FXML private TextField textH;
    @FXML private Label label1;
    @FXML private Button boton1;
    @FXML private ComboBox combo1;

    private Controller controller;
    private VistaCompra vistaCompra;
    private OpereacionesCliente opereacionesCliente;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        opereacionesCliente = new OpereacionesCliente();
        combo1.getItems().add("Boleto Pagado");
        combo1.getItems().add("Boleto Sin Pagar");
        combo1.setValue("Boleto Sin Pagar");

        textA.setDisable(true);
        textE.setDisable(true);
        textF.setDisable(true);


        boton1.setOnMousePressed((event)->{
            Cliente cliente = new Cliente();
            Seccion seccion = new Seccion();
            Compra compra = new Compra();

            cliente.setClienteId(vistaCompra.getClienteID());
            cliente.setNombre(textB.getText());
            cliente.setApellidos(textC.getText());
            cliente.setDireccion(textD.getText());


            compra.setCompraId(vistaCompra.getCompraId());
            compra.setClienteId(vistaCompra.getClienteID());
            compra.setNumBoleto(vistaCompra.getNumBoleto());
            if(combo1.getValue().equals("Boleto Pagado")){
                compra.setPagado(true);
            } else {
                compra.setPagado(false);
            }
            compra.setFechaHora(vistaCompra.getFechaHora());

            seccion.setSeccionId(vistaCompra.getSeccionId());
            seccion.setDescipcion(textG.getText());
            seccion.setPrecio(Float.parseFloat(textH.getText()));

            controller.getRegistros();
            opereacionesCliente.updateRegistro(compra,cliente,seccion);

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.close();



        });

    }


    public void setController(Controller controller,VistaCompra vistaCompra){
        this.controller = controller;
        this.vistaCompra = vistaCompra;
        this.cargarDatos();
    }


    public void cargarDatos(){
        textA.setText(vistaCompra.getClienteID()+"");
        textB.setText(vistaCompra.getClienteNombre());
        textC.setText(vistaCompra.getClienteApellidos());
        textD.setText(vistaCompra.getClienteDireccion());


        textE.setText(vistaCompra.getNumBoleto()+"");
        if(vistaCompra.isPagado()){
            combo1.setValue("Boleto Pagado");
        } else {
            combo1.setValue("Boleto Sin Pagar");
        }

        label1.setText(new SimpleDateFormat("yyyy-mm-dd").format(vistaCompra.getFechaHora()));

        textF.setText(vistaCompra.getSeccionId()+"");
        textG.setText(vistaCompra.getSeccionDescripcion());
        textH.setText(vistaCompra.getSeccionPrecio()+"");
    }
}
