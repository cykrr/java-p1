package Gui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;

public class Alerta {
	private static ButtonType yesButton = new ButtonType("Si", ButtonBar.ButtonData.OK_DONE);
	private static ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
	
	/* Sobrecarga Metodos
	 * mostrarAlertaAdvertencia()
	 * */
	
	/*
	 * Se encarga de mostrar una ventana de Alert tipo
	 * WARNING usando el String recibido como encabezado.
	 * No retorna nada y cierra la ventana con la X o con "OK"
	 * */
	public static void mostrarAlertaAdvertencia(String header) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.showAndWait();		
	}
	
	
	/*
	 * Se encarga de mostrar una ventana de Alert tipo
	 * WARNING usando los String recibidos como encabezado y mensaje.
	 * La ventana WARNING actua como una de Alert tipo CONFRMATIO, 
	 * tiene un boton "No", y tambien posee un boton "Si" que al presionar 
	 * retorna true, en caso de no ser presionado retorna false.
	 * */
	public static boolean mostrarAlertaAdvertencia(String header, String headerText) {
		Alert alert = new Alert(AlertType.WARNING,
						header,
		                yesButton,
		                noButton);
		alert.setTitle("Cerrando Programa");
		alert.setHeaderText(headerText);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.orElse(noButton) == yesButton)
			return true;
		return false;
	}
	
	/* FIN Sobrecarga Metodos
	 * mostrarAlertaAdvertencia()
	 * */
	
	
	/*
	 * Se encarga de mostrar una ventana de Alert tipo
	 * CONFIRMATION usando el String recibido como encabezado.
	 * La ventana CONFIRMATION de este metodo tiene un boton "Cancelar", y tambien
	 * posee un boton "Aceptar" que al presionar retorna true, en caso de no
	 * ser presionado retorna false.
	 * */
	public static boolean mostrarAlertaConfirmacion(String header) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmar");
		alert.setHeaderText(header);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == ButtonType.OK)
			return true;
		return false;
	}
	
	
	/*
	 * Se encarga de mostrar una ventana de Alert tipo
	 * ERROR usando los String recibidos como encabezado y mensaje.
	 * No retorna nada y cierra la ventana con la X o con "OK"
	 * */
	public static void mostrarAlertaError(String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}