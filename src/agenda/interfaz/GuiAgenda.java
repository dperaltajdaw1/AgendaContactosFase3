package agenda.interfaz;

import agenda.modelo.AgendaContactos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private java.awt.TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private java.awt.TextField txtBuscar;

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
		return panel;
	}

	private VBox crearPanelBotones() {
		// a completar 
		VBox panel = new VBox();
		panel.setPadding(new Insets(10));
		
		txtBuscar = new TextField();
		txtBuscar.getStyleClass().add("text-field");
		txtBuscar.minHeight(40);
		txtBuscar.setPromptText("Buscar");
		
		rbtListarTodo = new RadioButton();
		rbtListarTodo.getStyleClass().add("radio-button");
		rbtListarTodo.setPadding(new Insets(40,0,10,0));
		rbtListarTodo.setSelected(true);
		rbtListarTodo.setText("Listr toda la agenda");
		
		rbtListarSoloNumero = new RadioButton();
		rbtListarSoloNumero.getStyleClass().add("radio-button");
		rbtListarSoloNumero.setPadding(new Insets(0,0,10,0));
		rbtListarSoloNumero.setText("Listr nº contactos");
		
		btnListar = new Button();
		btnListar.getStyleClass().add("botones");
		btnListar.setText("Listar");
		
		btnPersonalesEnLetra = new Button();
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setText("Contactos Personales en letra");
		
		btnPersonalesOrdenadosPorFecha = new Button();
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setText("Contactos Personales ordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> {
			personalesOrdenadosPorFecha();
		});
		
		btnClear = new Button();
		btnClear.getStyleClass().add("botones");
		btnClear.setText("Clear");
		
		btnSalir = new Button();
		btnSalir.getStyleClass().add("botones");
		btnSalir.setText("Salir");
		
		panel.getChildren().addAll(
				txtBuscar, rbtListarTodo, rbtListarSoloNumero,
				btnListar, btnPersonalesEnLetra, btnPersonalesOrdenadosPorFecha,
				btnClear, btnSalir);
		return panel;
	}

	private GridPane crearPanelLetras() {
		// a completar
		GridPane panel = new GridPane();

		return panel;
	}

	private MenuBar crearBarraMenu() {
		// a completar
		MenuBar barra = new MenuBar();

		Menu me1 = new Menu("Archivo");
		Menu me2 = new Menu("Operaciones");
		Menu me3 = new Menu("Help");
		
		itemImportar = new MenuItem("Importar agenda");
		itemImportar.setOnAction(e -> {
		    importarAgenda();
		    itemImportar.setDisable(true);
		    itemExportarPersonales.setDisable(false);
		});
		itemExportarPersonales = new MenuItem("Exportar Personales");
		itemExportarPersonales.setDisable(true);
		itemExportarPersonales.setOnAction(e -> {
			exportarPersonales();
		});
		itemSalir = new MenuItem("Salir");
		itemSalir.setOnAction(e -> {
			salir();
		});
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		
		me1.getItems().add(itemImportar);
		me1.getItems().add(itemExportarPersonales);
		me1.getItems().add(separator);
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
		
		barra.getMenus().add(me1);
		barra.getMenus().add(me2);
		barra.getMenus().add(me3);
		
		itemExportarPersonales.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		itemImportar.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
		itemSalir.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		
		itemBuscar.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		itemFelicitar.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		
		itemAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
		
		return barra;
	}

	private void importarAgenda() {
		// a completar

		//FileChooser cho = new FileChooser();
		//File fie = cho.showSaveDialog(null);
		// No se como va el FileChooser
		
		//Esto es copia del importar del AgendaIO
		/*int error = 0;
		BufferedReader entrada = null;
		try
		{
			InputStream input = AgendaIO.class.getClassLoader().getResourceAsStream(texto);
			entrada = new BufferedReader(new InputStreamReader(input));
			String linea = entrada.readLine();
			while (linea != null){
				try {
					agenda.añadirContacto(parsearLinea(linea));
				}
				catch (NullPointerException io) {
					error ++;
				}
				linea = entrada.readLine();
			}
		}
		catch (IOException e){
			System.out.println("Error al leer " + texto);
			error++;
		}
		finally
		{
			if (entrada != null)
			{
				try
				{
					entrada.close();
				}
				catch (NullPointerException e)
				{
					System.out.println(e.getMessage());
					error++;
				}
				
				catch (IOException e)
				{
					System.out.println(e.getMessage());
					error++;
				}
			}
		} */
		//Fin de copia
		
		// Esto deberia ponerlo en el textarea
		//String ing = "N� de errores: " + error;
		//areaTexto.insert(ing,0);
	}

	private void exportarPersonales() {
		// a completar

	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		// a completar

	}

	private void personalesOrdenadosPorFecha() {
		clear();
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar

	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		// a completar

	}

	private void buscar() {
		clear();
		// a completar
		
		cogerFoco();
		//Para cogerlo (No puedo comprobar error)
		//String ing = txtBuscar.getText();
		
		//Para mostrarlo
		//areaTexto.setText();

	}

	private void about() {
		// a completar

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
