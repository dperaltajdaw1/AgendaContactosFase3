package agenda.interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import agenda.modelo.Profesional;
import agenda.modelo.Relacion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("/application.css")
		                    .toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();
		panel.setPadding(new Insets(10));
		panel.setTop(crearPanelLetras());
		
		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");
		
		panel.setCenter(areaTexto);
		panel.setLeft(crearPanelBotones());
		panel.setTop(crearPanelLetras());
		return panel;
	}

	private VBox crearPanelBotones() {
		// a completar
		VBox panel = new VBox();// Nuevo panel
		panel.setPadding(new Insets(10));// Padding
		
		txtBuscar = new TextField();// Zona de buscar
		txtBuscar.getStyleClass().add("text-field");
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		txtBuscar.minHeight(40);
		txtBuscar.setPromptText("Buscar");
		btnListar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			 public void handle(KeyEvent keyEvent) {
				 buscar();
		}}); 
		
		ToggleGroup group = new ToggleGroup();// Para seleccionar si quieres listar la agenda o el nº de contactos
		
		rbtListarTodo = new RadioButton();
		rbtListarTodo.getStyleClass().add("radio-button");
		VBox.setMargin(rbtListarTodo, new Insets(0, 0, 10, 0));
		rbtListarTodo.setSelected(true);
		rbtListarTodo.setText("Listr toda la agenda");
		rbtListarTodo.setToggleGroup(group);
		
		rbtListarSoloNumero = new RadioButton();
		rbtListarSoloNumero.getStyleClass().add("radio-button");
		rbtListarSoloNumero.setText("Listr nº contactos");
		rbtListarSoloNumero.setToggleGroup(group);
		
		
		btnListar = new Button();// Boton listar
		btnListar.getStyleClass().add("botones");
		VBox.setMargin(btnListar, new Insets(10, 0, 40, 0));
		btnListar.setPrefWidth(250);
		btnListar.setText("Listar");
		btnListar.setOnAction(e -> {
			listar();
		});
		
		btnPersonalesEnLetra = new Button();// Boton para los contactos personales en letra
		btnPersonalesEnLetra.getStyleClass().add("botones");
		VBox.setMargin(btnPersonalesEnLetra, new Insets(0, 0, 10, 0));
		btnPersonalesEnLetra.setPrefWidth(250);
		btnPersonalesEnLetra.setText("Contactos Personales en letra");
		btnPersonalesEnLetra.setOnAction(e -> {
			contactosPersonalesEnLetra();
		});
		
		btnPersonalesOrdenadosPorFecha = new Button();// Boton para los contactos personales en letra ordenados por fecha
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		btnPersonalesOrdenadosPorFecha.setText("Contactos Personales\n ordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> {
			personalesOrdenadosPorFecha();
		});
		
		btnClear = new Button();// Para limpiar el texto
		btnClear.getStyleClass().add("botones");
		VBox.setMargin(btnClear, new Insets(40, 0, 10, 0));
		btnClear.setPrefWidth(250);
		btnClear.setText("Clear");
		btnClear.setOnAction(e -> {
			clear();
		});
		
		btnSalir = new Button();// Para salir
		btnSalir.getStyleClass().add("botones");
		btnSalir.setPrefWidth(250);
		btnSalir.setText("Salir");
		btnSalir.setOnAction(e -> {
			salir();
		});
		
		panel.getChildren().addAll(
				txtBuscar, rbtListarTodo, rbtListarSoloNumero,
				btnListar, btnPersonalesEnLetra, btnPersonalesOrdenadosPorFecha,
				btnClear, btnSalir);
		return panel;
	}

	private GridPane crearPanelLetras() {


		GridPane panel = new GridPane();// Nuevo panel
		panel.setPadding(new Insets(10));// Padding
		// Las letras
		char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		int c = 0;
		int f = 0;
		for(int i = 0; i < letras.length; i++) {// Se van poniendo los botones por letra. Cuando llega al 14, pasa de fila
			if(f < 14) {
			Button botones = new Button();
			GridPane.setMargin(botones, new Insets(2));
			
			botones.getStyleClass().add("botonletra");
			botones.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			botones.setText(String.valueOf(letras[i]));
			char letra = letras[i];
			botones.setOnAction(e -> {
				contactosEnLetra(letra);
				}
			);
			
			
			panel.setHgrow(botones, Priority.ALWAYS);
			panel.setVgrow(botones, Priority.ALWAYS);
			
				panel.add(botones, f, c);
				f ++;
			}
			else {
				f = 0;
				c = 1;
				i --;
			}
		}
		
		return panel;
	}


	private MenuBar crearBarraMenu() {
		// a completar
		MenuBar barra = new MenuBar();

		Menu me1 = new Menu("Archivo");// Menu 1
		Menu me2 = new Menu("Operaciones");// Menu 2
		Menu me3 = new Menu("Help");// Menu 3
		
		itemImportar = new MenuItem("Importar agenda");
		itemImportar.setOnAction(e -> {
		    importarAgenda();
		    itemImportar.setDisable(true);// Cuando importar se realiza, se desactiva
		    itemExportarPersonales.setDisable(false);// y se reactiva exportar
		});
		itemExportarPersonales = new MenuItem("_Exportar Personales");
		itemExportarPersonales.setOnAction(e -> {
			exportarPersonales();
		});
		itemExportarPersonales.setDisable(true);
		itemSalir = new MenuItem("Salir");
		itemSalir.setOnAction(e -> {
			salir();
		});
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		
		me1.getItems().add(itemImportar);
		me1.getItems().add(itemExportarPersonales);
		me1.getItems().add(separator);// Para separar
		me1.getItems().add(itemSalir);
		
		itemBuscar = new MenuItem("Buscar");
		itemBuscar.setOnAction(e -> {
			buscar();
		});
		itemFelicitar = new MenuItem("Felicitar");
		itemFelicitar.setOnAction(e -> {
			felicitar();
		});
		
		me2.getItems().add(itemBuscar);
		me2.getItems().add(itemFelicitar);
		
		itemAbout = new MenuItem("About");
		itemAbout.setOnAction(e -> {
			about();
		});
		
		me3.getItems().add(itemAbout);
		
		barra.getMenus().add(me1);// Se ponen en la barra
		barra.getMenus().add(me2);
		barra.getMenus().add(me3);
		// Se les asignan combinaciones
		itemExportarPersonales.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		itemImportar.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
		itemSalir.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		
		itemBuscar.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		itemFelicitar.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		
		itemAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
		
		return barra;
	}
	/**
	* En este método conseguiremos crear un cuadro de dialogo el cual permitirá seleccionar el 
	* documento agenda.csv
	*/

	private void importarAgenda() {
		// a completar
		Stage st = new Stage();
		FileChooser selector = new FileChooser();
		selector.setTitle("Inportar agenda");
		selector.setInitialDirectory(new File("."));
		selector.getExtensionFilters()
		.addAll(new ExtensionFilter("csv",
		"*.csv"));
		File f = selector.showOpenDialog(st);
		if (f != null) {
            AgendaIO.importar(agenda, f.getName());
            areaTexto.setText("Agenda importada");
        }
	
	}

	/**
	* Se seleccionará el nombre del fichero en el que se podrán guardar los datos personales de la 
	* agenda agrupados por relación 
	*/
	private void exportarPersonales() {
		// a completar
		Stage st = new Stage();
		FileChooser save = new FileChooser();
		save.setTitle ("Guardar Documento");
		save.setInitialFileName("");
		save.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text file", "*.txt"));
		try {
			File file = save.showSaveDialog(st);
			AgendaIO.exportar(agenda, String.valueOf(file));
			save.setInitialDirectory(file.getParentFile());
			areaTexto.setText("Exportados datos personales");
		}
		catch(Exception e){
			
		}
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		// a completar
		if(agenda.totalContactos() == 0) {
			areaTexto.setText("Importe antes la agenda");
		}
		else if(rbtListarTodo.isSelected()) {
			areaTexto.setText(agenda.toString());
		}
		else {
			areaTexto.setText("Total contactos en la agenda: " + agenda.totalContactos());
		}
	}

	/**
	* Si no se ha llegado a importar la agenda se indicará con un mensaje, en el caso de lo 
	* contrario se ejecutará un cuadro de dialogo que permitirá mostrar los contactos en la letra 
	* seleccionada en dicho cuadro
	*/
	private void personalesOrdenadosPorFecha() {
		clear();
		if (agenda.totalContactos() == 0) {
			areaTexto.setText("Importe antes la agenda");
		} 
		else {
			List<Character> opciones = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R',
					'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
			ChoiceDialog<Character> dialogo = new ChoiceDialog<>('A', opciones);
			dialogo.setTitle("Selector de letra");
			dialogo.setContentText("Elija una letra");
			Optional<Character> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String msj = "";
				try {
					List<Personal> per = agenda.personalesOrdenadosPorFechaNacimiento(resul.get());
					int num = 0;
					for(Personal c : per) {
						msj += c.toString() + "\n";
						num ++;
					}
					areaTexto.setText("Contactos personales en la letra " + resul.get() + " (" + num + " contacto/s)\n\n"
							+ msj);
				}
				catch(NullPointerException e) {
					areaTexto.setText("La " + resul.get() + " no esta en la agenda");
				}
			} 
		}
		

	}

	/**
	* Si no se ha importado la agenda se indicará con un mensaje, en el caso de lo contrario se 
	* ejecutará un cuadro de dialogo que permitirá mostrar los contactos en la letra seleccionada 
	* en dicho cuadro
	*/
	private void contactosPersonalesEnLetra() {
		clear();
		// a completar
		if (agenda.totalContactos() == 0) {
			areaTexto.setText("Importe antes la agenda");
		} 
		else {
			List<Character> opciones = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R',
					'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
			ChoiceDialog<Character> dialogo = new ChoiceDialog<>('A', opciones);
			dialogo.setTitle("Selector de letra");
			dialogo.setContentText("Elija una letra");
			Optional<Character> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String msj = "";
				try {
					ArrayList<Personal> per = agenda.personalesEnLetra(resul.get());
					int num = 0;
					for(Personal c : per) {
						msj += c.toString() + "\n";
						num ++;
					}
					areaTexto.setText("Contactos personales en la letra " + resul.get() + " (" + num + " contacto/s)\n\n"
							+ msj);
				}
				catch(NullPointerException e) {
					areaTexto.setText("La " + resul.get() + " no esta en la agenda");
				}
			} 
		}


	}
	/**
	 * Si no se ha importado la agenda se indicará con un mensaje, en el caso de lo contrario se 
	 * muestran los contactos existentes con dicha letra
	 * @param letra
	 */
	private void contactosEnLetra(char letra) {
		clear();
		String texto = "";
		if(agenda.totalContactos() != 0) {
			try {
				Set<Contacto> contactos = agenda.contactosEnLetra(letra);
				for(Contacto c : contactos) {
					texto += c.toString() + "\n";
				}
				areaTexto.setText("Contactos con la letra " + letra + "\n\n" + texto);
			}
			catch(NullPointerException e) {
				areaTexto.setText("No hay contactos existentes");
			}
		}
		else {
			areaTexto.setText("No has imporatado la agenda");
		}
	}
	
	/**
	* En el caso de que no se importe la agenda se indicará con un mensaje
	* En cambio si la agenda ha sido exportada correctamente se mostrará la fecha actual y la 
	* relación de contactos personales a los que hay que felicitar
	*/
	private void felicitar() {
		clear();
		String felicitacion = " ";
		
		if (agenda.totalContactos() != 0) {
			felicitacion = "Hoy es " + LocalDate.now() + "\n";
				if(agenda.felicitar().isEmpty()) {
					areaTexto.setText("Hoy no hay nadie a quien felicitar\n");
				}
				else {
					areaTexto.setText("¡Es el cumpleaños de " + agenda.felicitar() + "!\n");
					
				}
				areaTexto.appendText(felicitacion);
		}		
		
		else {
			areaTexto.setText("No has imporatado la agenda\n");
		}

	}

	/**
	* Si se pulsa Intro en la caja de se mostrará los contactos de la agenda que coincidan con el 
	* texto introducido
	* Si no se ha tecleado nada en la caja de texto se indica con un mensaje. 
	* Si no hay contactos que incluyan ese texto se indica también con un mensaje.
	*/
	private void buscar() {
		clear();
		// a completar

		cogerFoco();
		//Para cogerlo
		String ing = txtBuscar.getText();
		if (ing == "Buscar") { //Si no hay nada en el txtBuscar, no buscará y dejará un aviso
			areaTexto.setText("Para buscar, se debe introducir antes lo que se busca");
		}
		else {
		//Para mostrarlo
			List <Contacto> tac = agenda.buscarContactos(ing);
			if (tac.size()<1) {
				areaTexto.setText("No hay contactos asi");
			}
			else {
				for (Contacto to : tac) {
					String tri = to.getNombre() + to.getApellidos();
					areaTexto.setText(tri);
				}
				
			}
			
		}
		// Devuelve focus a AreaTexto
		areaTexto.requestFocus();
		areaTexto.selectAll();
	}
	/**
	* Se mostrará un cuadro de dialogo “ALERT”  
	*/
	private void about() {
		clear();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Agenda de Contactos");
		alert.setContentText("Mi agenda de contactos");
		alert.setHeaderText(null);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().
		getResource("/application.css").toExternalForm());
		alert.showAndWait();

	}

	private void clear() {
		areaTexto.setText("");
	}

	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
