package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    @FXML private TableView table;

    private OpereacionesCliente opereacionesCliente;
    private Cliente cliente;
    private Boleto boleto;
    private Compra compra;
    private Seccion seccion;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        opereacionesCliente = new OpereacionesCliente();
        cliente = new Cliente();
        boleto = new Boleto();
        compra = new Compra();
        seccion = new Seccion();

        //agragr columnas al table view
        table.getColumns().clear();
        TableColumn colCompraId = new TableColumn<>("compraId");
        TableColumn colNumBol = new TableColumn<>("numBoleto");
        TableColumn colPagado = new TableColumn<>("pagado");
        TableColumn colFechaHora = new TableColumn<>("fechaHora");
        TableColumn colClienteId = new TableColumn<>("clienteID");
        TableColumn colClienteNombre = new TableColumn<>("clienteNombre");
        TableColumn colClienteApellidos = new TableColumn<>("clienteApellidos");
        TableColumn colClienteDireccion = new TableColumn<>("clienteDireccion");
        TableColumn colSeccionId = new TableColumn<>("seccionId");
        TableColumn colSeccionDescripcion = new TableColumn<>("seccionDescripcion");
        TableColumn colSeccionPrecio = new TableColumn<>("seccionPrecio");
        TableColumn colBotonUpdate = new TableColumn<>("botonUpdate");
        TableColumn colBotonDelete = new TableColumn<>("botonDelete");

        table.getColumns().add(colClienteId);
        table.getColumns().add(colClienteNombre);
        table.getColumns().add(colClienteApellidos);
        table.getColumns().add(colClienteDireccion);
        table.getColumns().add(colSeccionId);
        table.getColumns().add(colSeccionDescripcion);
        table.getColumns().add(colSeccionPrecio);
        table.getColumns().add(colCompraId);
        table.getColumns().add(colNumBol);
        table.getColumns().add(colPagado);
        table.getColumns().add(colFechaHora);
        table.getColumns().add(colBotonUpdate);
        table.getColumns().add(colBotonDelete);


        colClienteNombre.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        colClienteApellidos.setCellValueFactory(new PropertyValueFactory<>("clienteApellidos"));
        colClienteDireccion.setCellValueFactory(new PropertyValueFactory<>("clienteDireccion"));
        colSeccionId.setCellValueFactory(new PropertyValueFactory<>("seccionId"));
        colSeccionDescripcion.setCellValueFactory(new PropertyValueFactory<>("seccionDescripcion"));
        colSeccionPrecio.setCellValueFactory(new PropertyValueFactory<>("seccionPrecio"));
        colBotonUpdate.setCellValueFactory(new PropertyValueFactory<>("botonUpdate"));
        colBotonDelete.setCellValueFactory(new PropertyValueFactory<>("botonDelete"));
        colCompraId.setCellValueFactory(new PropertyValueFactory<>("compraId"));
        colNumBol.setCellValueFactory(new PropertyValueFactory<>("numBoleto"));
        colPagado.setCellValueFactory(new PropertyValueFactory<>("pagado"));
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        colClienteId.setCellValueFactory(new PropertyValueFactory<>("clienteID"));




        getRegistros();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String fecha = dateFormat.format(date);
        label1.setText(fecha);

        combo1.getItems().add("Boleto Sin Pagar");
        combo1.getItems().add("Boleto Pagado");
        combo1.setValue("Boleto Sin Pagar");


        textA.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() < 9 && newValue.length() > 0){
                    cliente.setClienteId(Integer.parseInt(newValue));
                }
            }
        });

        cliente.nombreProperty().bind(textB.textProperty());
        cliente.apellidosProperty().bind(textC.textProperty());
        cliente.direccionProperty().bind(textD.textProperty());

        textE.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 0){
                    boleto.setBoletoId(Integer.parseInt(newValue));
                }
            }
        });

        combo1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if(newValue.equals("Boleto Pagado")){
                    compra.setPagado(true);
                } else {
                    compra.setPagado(false);
                }

            }
        });

        textF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 0){
                    seccion.setSeccionId(Integer.parseInt(newValue));
                }
            }
        });

        seccion.descipcionProperty().bind(textG.textProperty());

        textH.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 0){
                    seccion.setPrecio(Float.parseFloat(newValue));
                }
            }
        });

        compra.clienteIdProperty().bind(cliente.clienteIdProperty());
        compra.numBoletoProperty().bind(boleto.boletoIdProperty());
        boleto.seccionIDProperty().bind(seccion.seccionIdProperty());







        boton1.setOnMousePressed((event) -> {

            try {
                compra.setFechaHora(new SimpleDateFormat("dd/MM/YY").parse(this.label1.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            opereacionesCliente.insertCliente(cliente);
            opereacionesCliente.insertBoleto(boleto,seccion,compra);
            limpiarCampos();
            getRegistros();
        });





    }


    public void getRegistros(){
        this.table.getItems().clear();
        ArrayList<VistaCompra> arrayList = new ArrayList<>();
        arrayList = opereacionesCliente.getRegistros();

        if(arrayList.size() > 0){

            int i = 0;
            for (VistaCompra vistaCompra:arrayList) {
                vistaCompra.getBotonUpdate().setId(i+"");
                vistaCompra.getBotonDelete().setId(i+"");
                vistaCompra.getBotonUpdate().setOnMousePressed((event) -> {
                    int row = Integer.parseInt(((Button)event.getSource()).getId());
                    VistaCompra vista = (VistaCompra) this.table.getItems().get(row);
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        AnchorPane root = loader.load(getClass().getResourceAsStream("update.fxml"));
                        UpdateController updateController = (UpdateController) loader.getController();
                        updateController.setController(this,vista);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
                vistaCompra.getBotonDelete().setOnMousePressed((event)->{
                    int row = Integer.parseInt(((Button)event.getSource()).getId());
                    VistaCompra vista = (VistaCompra) this.table.getItems().get(row);
                    this.opereacionesCliente.deleteCompra(vistaCompra);
                    this.getRegistros();
                });
                this.table.getItems().add(vistaCompra);
                i++;
            }


        }

    }


    public void limpiarCampos(){
        textA.setText("");
        textB.setText("");
        textC.setText("");
        textD.setText("");
        textC.setText("");
        textD.setText("");
        textE.setText("");
        textF.setText("");
        combo1.setValue("Boleto Sin Pagar");
    }


}
