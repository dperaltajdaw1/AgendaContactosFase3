package agenda.test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import agenda.io.AgendaIO;
import agenda.modelo.*;

/**
 * @version 1.2,
 * @author Jhon Vera, Diana Peralta, Adrian Vitoria
 * Main para probar todos los metodos de la clase AgendaContactos
 */  

public class TestAgenda {

	public static void main(String[] args) throws Exception { 
		AgendaContactos agenda = new AgendaContactos();
		; /*Si se cambia el throw de importar, tambien hay que cambiarlo en el test*/
		separador();
		System.out.println(AgendaIO.importar(agenda,"agenda.csv")+" lineas erroneas");
		System.out.println("AGENDA DE CONTACTOS\n");
		System.out.println(agenda);
		separador();
		buscarContactos(agenda, "acos");
		separador();

		buscarContactos(agenda, "don");
		separador();

		felicitar(agenda);
		separador();

		personalesOrdenadosPorFecha(agenda, 'm');
		separador();
		personalesOrdenadosPorFecha(agenda, 'e');
		separador();
		personalesOrdenadosPorFecha(agenda, 'w');
		separador();

		AgendaIO.exportar(agenda,"personales-relacion.txt");

	}

	private static void buscarContactos(AgendaContactos agenda, String texto) {
		List<Contacto> resultado = agenda.buscarContactos(texto);
		System.out.println("Contactos que contienen \"" + texto + "\"" + "\n");
		if (resultado.isEmpty()) {
			System.out.println("No hay contactos coincidentes");
		} else {
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void felicitar(AgendaContactos agenda) {
		System.out.println("Fecha actual: " + LocalDate.now());
		List<Personal> resultado = agenda.felicitar();
		if (resultado.isEmpty()) {
			System.out.println("Hoy no cumple nadie");
		} else {
			System.out.println("Hoy hay que felicitar a ");
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void personalesOrdenadosPorFecha(AgendaContactos agenda,
			char letra) {
		System.out.println("Personales en letra " + letra
				+ " ordenados de < a > fecha de nacimiento");
		List<Personal> personales = agenda.personalesEnLetra(letra);
		if (personales == null) {
			System.out.println(letra + " no está en la agenda");
		} else {
			agenda.personalesOrdenadosPorFechaNacimiento(letra)
					.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void separador() {
		System.out.println(
				"------------------------------------------------------------");

	}

}
