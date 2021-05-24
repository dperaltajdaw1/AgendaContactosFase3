 package agenda.modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * @version 1.2
 * @author Jhon Vera, Diana Peralta, Adrian Vitoria
 * Simula una agenda de contactos 
 */ 

public class AgendaContactos {
	//Atributos
	private Map<Character, Set<Contacto>> agenda;
	
	//Constructor
	public AgendaContactos() {
		this.agenda = new TreeMap<Character, Set<Contacto>>();
	}
	
	/**
	 * Dado un contacto lo añade a la Agenda
	 * @param Contacto que se va añadir  
	 */
	public void añadirContacto(Contacto con) { // Adrian
		
		if (agenda.containsKey(con.getPrimeraLetra()) == false) { // Comprueba si existe una clave que coincida con la inicial del apellido
			LinkedHashSet<Contacto> tes = new LinkedHashSet<>(); // Si no existe, la crea
			tes.add(con);
			agenda.put(con.getPrimeraLetra(), tes);
		}
		else {
			LinkedHashSet<Contacto> hsa = (LinkedHashSet<Contacto>) agenda.get(con.getPrimeraLetra()); // Si existe, coge el set de contactos de dicha clave en una nueva, añade el nuevo contacto y sustituye a la vieja con la nueva  
			hsa.add(con);
			agenda.put(con.getPrimeraLetra(), hsa);
		}
		
	}
	
	/**
	 * Dada una letra se devuelve un conjunto (Set) de contactos en esa letra
	 * @param la letra de la coleccion Set que se busca
	 * @return Un conjunto set con los contactos de la letra buscada
	 */
	public Set<Contacto> contactosEnLetra(char letra) {
		Set <Contacto> contactos = new TreeSet<Contacto>(); //Crea un lista Set vacia
			contactos.addAll(agenda.get(letra)); // Añade a la lista los contactos de la respectiva letra
			return contactos; 
	}
	
	/**
	 * Devuelve el nº total de contactos en la agenda
	 * @return enumero de contactos que hay
	 */

	public int totalContactos() {
		int contador = 0; // crea un contador a 0 
		for(char key : agenda.keySet()) {
			contador += agenda.get(key).size(); // suma la contidad de contactos que tenga la clave
		}
		return contador;
	}

	/**
	 * Devuelve la representacion textual de la agenda
	 * @return La forma en la cual se quiere que se muestre la agenda
	 */
	@Override
	public String toString() {
		String salida = "";//Crea una variable de String local vacia
		for(char key : agenda.keySet()) {
			salida += key + " (" + agenda.get(key).size() + " contacto/s)\n"
					+ "---------------\n"; //Agrega a la cantidad total de contactos a la variable
			Iterator<Contacto> it = contactosEnLetra(key).iterator(); // crea un iterador
			while(it.hasNext()) {
				salida += it.next().toString() + "\n";//Usa el toString de contactos para agregarlo ala varible
			}
		}
		return salida + "\n("+ totalContactos() + " contacto/s)";
	}
	
	/**
	 * Devuelve una (ArryList) con todos los contactos que incluyan el texto indicado
	 * @param el string que sera buscado entre los contactos
	 * @return ArrayList con los contactos encontrados
	 */
	public List<Contacto> buscarContactos(String texto) {
		ArrayList <Contacto> contactos = new ArrayList<>();//Crea una ArrayList vacio
		for(char key : agenda.keySet()) {
			Iterator<Contacto> it = agenda.get(key).iterator();// Crea un Iterador
			while(it.hasNext()) {
				Contacto temp = it.next();//Guarda un contacto en la variable local
				if(temp.getNombre().contains(texto.toUpperCase()) || temp.getApellidos().contains(texto.toUpperCase())) {//Verifica que el nombre o apellidos del contacto contenga el texto 
					contactos.add(temp);//Añade el contacto al Arraylist;
				}
			}
		}

		return contactos;

	}
	
	/**
	 * Dada una letra devuelve un (ArrayList) con los contactos personales en esa letra.
	 * @param letra en la cual se queire buscar
	 * @return ArrayList con los contactos encontrados en la letra , null si la letra no esta en la agenda 
	 */
	public ArrayList<Personal> personalesEnLetra(char letra) { // Adrian

		ArrayList<Personal> ray = new ArrayList<>(); // Crea arraylist vacio
		if (agenda.containsKey(Character.toUpperCase(letra))) { // Comprueba si la letra existe entre las claves
			for (Contacto con : agenda.get(Character.toUpperCase(letra))) { // Si existe, por cada contacto en dicha letra
				if (  (con instanceof Personal) ) { // se comprueba que sea clase Personal
					ray.add((Personal) con); // Si lo es, se añade al arraylist
				}
			}
			if (ray.isEmpty()) { // Si en la letra clave no detecta personales, devuelve null
				return null; 
			}
		}
		else { // Si clave no existe
			return null; // Devuelve null
		}
		return ray; // Devuelve el arraylist
	}
	
	/**
	 * Devuelve un (ArrayList) con todos los contactos personales a los que hay que felicitar
	 * @return ArrayList con los contactos a felicitar
	 */
	public List<Personal> felicitar() {
		List<Personal> cumple = new ArrayList<Personal>();//Crea un ArrayList vacio;
		for(char key : agenda.keySet()) {
			Iterator<Contacto> it = agenda.get(key).iterator();//Crea un iterador
			while(it.hasNext()) {
				Contacto temp = it.next();//Guarda un contacto en la varible
				if(temp.getClass().equals(Personal.class)) {// Verifica que la clase pertenezca a la clase Personal
					Personal p = (Personal)temp;//Realiza un cast a la varible temp
					if(p.esCumpleaños() == true) {//Comprueba que sea el cumpleaños de dicha persona
						cumple.add(p);
					}
				}
			}
		}
		return cumple;
	}
	
	/**
	 * Devuelve un nuevo Map con todos los contactos personales ordenados por la relacio(clave) y
	 * un List(valor asociado) de String del nombre completo
	 * @return Nuevo Map(Relacion,list<String>) de los contactos personales
	 */
	public Map<Relacion,List<String>> personalesPorRelacion() {
		Map<Relacion,List<String>> relacion = new TreeMap<Relacion,List<String>>();//Crea un map vacio
		for(char key : agenda.keySet()) {
			Iterator<Contacto> it = agenda.get(key).iterator();//Crea un Iterador
			while(it.hasNext()) {
				Contacto temp = it.next();//Guarda un contacto en la varibel
				if(temp.getClass().equals(Personal.class)) {
					Personal p = (Personal)temp;//Se realiza un cast 
					if(relacion.containsKey(p.getRelacion()) == true) {//Verifica si la relacion(clave) ya existe en el Map
						relacion.get(p.getRelacion()).add(p.getApellidos() + " " + p.getNombre());//Agrega el nombre completo en el List asociado a la clave
					}
					else {//si no es el caso
						List<String> nom = new ArrayList<String>();// Crea una nueva lista
						nom.add(p.getApellidos() + " " + p.getNombre());//Agrega el nombre completo en el List
						relacion.put(p.getRelacion(), nom);//Agrega la nueva clave con la lista creada anteriormente
					}
				}
			}
		}
		return relacion;
	}

	/**
	 * Dada una letra devuelve los contactos personales ordenados por fecha de nacimiento(ascendente)
	 * @param letra de los contactos a ordenar
	 * @return List de contactos Personales ordenados por fecha de nacimiento
	 */
	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) { // Adrian

		List<Personal> ray = new ArrayList<>(); // Crea arraylist vacio
		if (agenda.containsKey(Character.toUpperCase(letra))) { // Comprueba si la letra existe entre las claves
			for (Contacto con : agenda.get(Character.toUpperCase(letra))) { // Si existe, por cada contacto en dicha letra
				if (  (con instanceof Personal) ) { // se comprueba que sea clase Personal
					ray.add((Personal) con); // Si lo es, se añade al arraylist
				}
			}
		}
		else { // Si clave no existe
			return null; // Devuelve null
		}
		
		Comparator<Personal> comfecnac = new Comparator<>() { // Para ordenar fechas de nacimiento con comparator
		    @Override
		    public int compare(Personal p1, Personal p2) {
		        return p1.getFecnac().compareTo(p2.getFecnac());
		    }
		};
		
		Collections.sort(ray, comfecnac); //Ordenar la arraylist con el comparator
		
		
		return ray; // Devuelve el arraylist

	}
	
	

}
