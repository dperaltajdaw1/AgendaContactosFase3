
package agenda.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @version 1.0,
 * @author Jhon Vera, Diana Peralta, Adrian Vitoria
 * Representacion de un conctacto Personal de la agenda
 */ 

public class Personal extends Contacto { // Padre: Contacto

	// Atributos
	private LocalDate fecnac; // Fecha nacimiento
	private Relacion relacion; // Relacion con el propietario, sacado de la clase Enum Relacion
	private String firma; // Firma fija para todos los contactos en personal
	
	// Constructor
	public Personal(String nombre, String apellidos, String telefono, String email, String fecnac,
			Relacion relacion) {
		super(nombre, apellidos, telefono, email); // Del super
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato que debería estar el String
		this.fecnac = LocalDate.parse(fecnac,formatter); // Recibe fecha nacimiento en String, pasar a LocalDate
		this.relacion = relacion; // Añade relación
		firma = "Un abrazo!!"; // Firma fija
	}
	
	//Getters y setters
	public LocalDate getFecnac() { // Getter de fecha de nacimiento
		return fecnac;
	}
	public void setFecnac(LocalDate fecnac) { // Setter de fecha de nacimiento
		this.fecnac = fecnac;
	}
	public Relacion getRelacion() { // Getter de relacion
		return relacion;
	}
	public void setRelacion(Relacion relacion) { // Setter de relacion
		this.relacion = relacion;
	}
	
	public String getFirmaEmail() { // Getter de firma
		return firma;
	}
	// Ya que firma no requiere cambios no tiene setter
	
	// comprobar si es su cumpleaños
	public boolean esCumpleaños() {
		return fecnac.getDayOfMonth() == LocalDate.now().getDayOfMonth() && fecnac.getMonthValue() == LocalDate.now().getMonthValue(); //Compara con dia actual
	}
	
	// para pasar la fecha de nacimiento al string de patrón especificado
	private String formatoFecha() {
		return fecnac.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); // Pasa la fecha a String con el patrón especificado y en minúsculas
	}
	
	// Añade al toString padre los atributo de Personal
	@Override
	public String toString() {
		return super.toString() + "Fecha nacimiento: " + this.formatoFecha() + "\nRelación: " + this.getRelacion() + "\n"; // Añade fecha nacimiento y relación
	}
}
