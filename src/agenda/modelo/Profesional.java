package agenda.modelo;

/**
 * @version 1.2,
 * @author Jhon Vera, Diana Peralta, Adrian Vitoria
 * Representacion de un conctacto personal de la agenda
 */ 

public class Profesional extends Contacto{
	
	//Atributos
	private String nombreEmpresa;
	private String[] firmaEmail = {"Atentamente", "Saludos coordiales", "Mis mejores deseos"};
	
	//Constructor
	public Profesional(String nombre, String apellidos, String telefono, String email, String nombreEmpresa) {
		//Atributos clase Contacto
		super(nombre, apellidos, telefono, email);
		//Separador para obtener la primera letra del nombre de la empresa en mayuscula
		this.nombreEmpresa = obtenerNombre(nombreEmpresa);
		
	}
	/*
	 * Uso este metodo privado para devolver el nombre de la empresa correctamente
	 * @param el nombre de la empresa 
	 * @return el nombre de la empresa  con las palabras capitalizadas
	 */
	private String obtenerNombre(String nombreEmpresa){
		nombreEmpresa = nombreEmpresa.trim();
		String[] nombre = nombreEmpresa.split(" ");
		nombreEmpresa = " ";
		char primeraLetra = ' ';
		String restoNombre = " ";
		
		for(int i = 0; i < nombre.length; i++) {
			primeraLetra = nombre[i].toUpperCase().charAt(0);
			restoNombre = nombre[i].substring(1).toLowerCase();
			nombreEmpresa += primeraLetra + restoNombre + " ";
		}
		return nombreEmpresa.trim();
	}
	
		//GETTERS
	
		/*
		 * @return el nombre de la empresa correctamente
		 */
		public String getNombreEmpresa() {
			return nombreEmpresa;
		}
		
		/*
		 * @return el mensaje del email aleatoriamente
		 */
		public String getFirmaEmail() {
			return firmaEmail[(int)(Math.random() * 3)];
		}
		
		//SETTER
		
		/*
		 * @param el nuevo nombre de la empresa
		 * @return el nombre de la empresa asignado correctamente
		 */
		public void setNombreEmpresa(String nombreEmpresa) {
			this.nombreEmpresa = nombreEmpresa;
		}
		
		/*
		 * @return todos los datos del contacto profesional
		 */
		public String toString() {
			return super.toString() + "Empresa: " + nombreEmpresa + "\n";
		}

}
