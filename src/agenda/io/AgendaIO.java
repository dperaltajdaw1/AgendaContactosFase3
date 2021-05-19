package agenda.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import agenda.modelo.*;

/**
 * @version 1.2
 * @author Jhon Vera, Diana Peralta, Adrian Vitoria Lee una liniea de datos para
 *         agregarlos a la agenda.
 */

public class AgendaIO {

	/**
	 * Guarda la información del metodo obtenerLineasDatos y añade la información a
	 * esta
	 * 
	 * @param agenda de la clase AgendaContactos
	 * @return
	 *  
	 */
	public static int importar(AgendaContactos agenda, String texto) {
		int error = 0;
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
		} 
		return error;
	
	}

	/**
	 * Dado el nombre del fichero guarda los contctos personales calsificados por su relacion
	 * en un fichero
	 * @param agenda de la cual se van a importar los contactos
	 * @param nombre del fichero
	 * 
	 * */
	public static void exportar(AgendaContactos agenda, String file) {
		FileWriter fw = null;

		try {
			fw = new FileWriter(file);
		}

		catch (IOException io) {
			System.out.println("Error al abrir el fichero");
		}
		// Escribimos

		try {
			String mensaje = "";
			for(Relacion key : agenda.personalesPorRelacion().keySet()) {
				mensaje += key.toString() + "\n\t";
				mensaje += agenda.personalesPorRelacion().get(key).toString() + "\n";
			}
			fw.write(mensaje);
			System.out.println("Fichero guardado");
		}
		
		catch (IOException io) {
			System.out.println("Error al escribir");
		}
		// cerramos el fichero

		finally {
			try {
				fw.close();
			} catch (IOException io) {
				System.out.println("Error al cerrar el archivo");
			}
		}
	}

	/**
	 * Guarda cada dato de cada contacto dentro de un Array
	 * 
	 * @param cada linea guardada en el metodo anterior
	 * @return un array de un nuevo contacto con su respectiva información
	 */
	private static Contacto parsearLinea(String linea){
		String[] datosLinea = linea.split(",");

		String nombre = datosLinea[1].trim();
		String apellidos = datosLinea[2].trim();
		String telefono = datosLinea[3].trim();
		String email = datosLinea[4].trim();
		String nombreEmpresa = "";
		Relacion relacion = null;
		String fechaNac = "";
		if (Integer.valueOf(datosLinea[0].trim()) == 2) {
			try {
				fechaNac = datosLinea[5].trim();
				relacion = Relacion.valueOf(datosLinea[6].trim().toUpperCase());
				 return new Personal(nombre, apellidos, telefono, email, fechaNac, relacion);
			}
			catch(DateTimeParseException e){
				e.getMessage();
			}
			catch(IllegalArgumentException e){
				e.getMessage();
			}
			return null;
		} 
		else {
			nombreEmpresa = datosLinea[5].trim();
			return new Profesional(nombre, apellidos, telefono, email, nombreEmpresa);
		}
		
	}

}
