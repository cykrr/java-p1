package Gui.Vistas.LeerPresentacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Congreso.Expositor;
import Congreso.Presentacion;
import Congreso.Registro;
import Congreso.Util;
import Congreso.excepciones.InvalidDuracionException;
import Congreso.excepciones.InvalidNombreException;
import Congreso.excepciones.NullExpositorException;
import Gui.Alerta;
import Gui.Vistas.PopUp;


/* Clase encargada de mostrar la ventana en caso de crear o editar presentacion */
public class LeerPresentacion extends PopUp implements PopUp.PopAble {
	
    // Elementos XML
    @FXML private ComboBox<Expositor> comboExpositor;
    @FXML private TextField tfNombre, tfHora, tfDuracion, tfDescripcion, tfAsistentes;
    @FXML private DatePicker dpFecha;
    @FXML private Button submit;

    // Referencia al registro principal
    private Registro registro;

    // Valor de retorno
    private Presentacion presentacion;
    
    private boolean editando = false;
    
    
    /**	@brief Constructor de la vista
     * 
     * Carga el archivo fxml que corresponde para abrirlo como ventana.
     * @param registro Es el atributo de clase Registro que contiene todas las presentaciones.
     * @param stage Atributo con el escenario que se muestra en ventana al agregar o editar una presentacion.
     * */
    public LeerPresentacion(Registro registro, Stage stage)  {
        super(stage, "/vistas/leerPresentacion.fxml");
        this.registro = registro;
        inicializar();
    }
    
    /**	@brief Constructor de la vista
     * 
     * Inicia los atributos de la presentacion como TextField para mostrarlos por ventana de no estar vacios.
     * Carga el archivo fxml que corresponde para abrirlo como ventana.
     * @param registro Es el atributo de clase Registro que contiene todas las presentaciones.
     * @param stage Atributo con el escenario que se muestra en ventana al agregar o editar una presentacion.
     * @param presentacion Es la presentacion cuya informacion se visualiza.
     * */
    public LeerPresentacion(Registro registro, Stage stage, Presentacion presentacion) {
		this(registro, stage);
		this.presentacion = presentacion;		
		editando = true;
		
		tfNombre.setText(presentacion.getNombre());
		dpFecha.setValue(presentacion.getFecha());
		tfHora.setText(presentacion.getStringHora());
		tfDuracion.setText(Integer.toString(presentacion.getDuracion()));
		tfDescripcion.setText(presentacion.getDescripcion());
		comboExpositor.setValue(presentacion.getExpositor());
	}
    
    /**
     * Se encarga del inicializar el comboBox iterativo que nos permite junto a
     * la ventana. De esta forma tenemos permitido escoger entre los expositores 
     * existentes al abrirse la ventana.
     * */
    public void inicializar() {
		ObservableList<Expositor> itemsExpositores = comboExpositor.getItems();
		Iterator<Expositor> iteratorExpositores = registro.getExpositores();
		
        while(iteratorExpositores.hasNext()) {
        	itemsExpositores.add(iteratorExpositores.next());
        }
    }
    
    /**
     * @return La presentacion casteada como Object
     * */
    @Override
    public Object getValue() {
        return this.presentacion;
    }
    
    /**
     * Lee lo ingresado por el usuario para la presentacion, confirma si existe algun 
     * error o valor no valido ingresado, en caso de haber, se abre una 
     * ventana de error con un mensaje de lo ingresado erroneamente, si no hay error, 
     * guarda la informacion agregando o editando una presentacion. 
     * */
    @Override
    public boolean guardar() {
        String nombre = tfNombre.getText().trim();
        String strFecha = dpFecha.getEditor().getText().trim();
        String strHora = tfHora.getText().trim();
        String strDuracion = tfDuracion.getText().trim();
        String descripcion = tfDescripcion.getText().trim();
        Expositor expositor = comboExpositor.getValue();
        
        if(nombre.isEmpty() || strFecha.isEmpty() || strHora.isEmpty() 
                || strDuracion.isEmpty() || descripcion.isEmpty()) {
        	Alerta.mostrarAlertaAdvertencia("No pueden quedar campos vacíos");
        	return false;
        }
        
        if(registro.buscarPresentacion(nombre) != null && editando == false) {
        	Alerta.mostrarAlertaAdvertencia("El nombre de la presentación ya existe");
        	return false;
        }
        
        LocalDate fecha;
        LocalTime hora;
        
        try {
        	fecha = Util.parseDate(strFecha);
        } catch(DateTimeParseException e) {
        	Alerta.mostrarAlertaAdvertencia("La fecha ingresada no es válida");
        	return false;
        }
        
        try {
        	hora = Util.parseTime(strHora);
        } catch(DateTimeParseException e) {
        	Alerta.mostrarAlertaAdvertencia("La hora ingresada no es válida");
        	return false;
        }
        
        if(!Util.isNumeric(strDuracion)) {
        	Alerta.mostrarAlertaAdvertencia("La duración ingresada no es válida");
        	return false;
        }
        
        int duracion = Integer.parseInt(strDuracion);
        
        try {
			presentacion = new Presentacion(nombre, expositor, fecha, hora, duracion, descripcion);
			return true;
		} catch (InvalidNombreException e) {
			Alerta.mostrarAlertaAdvertencia("El nombre no puede contener caracteres especiales");
		} catch (NullExpositorException e) {
			Alerta.mostrarAlertaAdvertencia("El expositor seleccionado no es válido");
		} catch (InvalidDuracionException e) {
			Alerta.mostrarAlertaAdvertencia("La duración debe estar en el rango de 1 a 300");
		}
        
        return false;
    }

}
