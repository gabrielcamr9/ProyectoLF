package automatas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Tipode dato abstracto para definir un automata.
 *
 */
public class AutomataDinamico {
	private ArrayList<Estado> listaEstados;
	private HashSet<Character> alfabeto;
	private HashSet<Estado> finales;
	private Estado inicial;
	private String cadena;
	private String original;

	private Estado atrapadoImplicito;

/**
 * Constructor por defecto
 * Crea el estado de atrapado implícito
 */
	public AutomataDinamico() {
		listaEstados = new ArrayList<>();
		alfabeto = new HashSet<>();
		finales = new HashSet<>();
		cadena = null;
		atrapadoImplicito = new Estado("atrapado");
	}
	
	/**
	 * Count Estados
	 * @return regresa lac cantidad de estados dentro del Automata
	 */
	public int getEstadosCount(){
		return listaEstados.size();
	}
	
	/**
	 * Retrieves an state given an indez
	 * @param i index to seach.
	 * @return the state, if found
	 */
	public Estado getEstadoWithIndex(int i){
		return listaEstados.get(i);
	}
	
	/**
	 * Agregar al alfabeto
	 * @param c caracter
	 */
	public void  agregarSimbolo(char c){
		alfabeto.add(c);
	}

	/**
	 * Agregar nuevo estado al automata.
	 * @param e
	 */
	public void agregarEstado(Estado e){
		listaEstados.add(e);
	}
	
	/**
	 * Set node as initial
	 * @param i
	 */
	public void setInitial(int i){
		listaEstados.get(i).setAsInicial(true);
		this.inicial = listaEstados.get(i);
	}
	
	/**
	 * Set node as final
	 * @param i
	 */
	public void settFinal(int i){
		listaEstados.get(i).setAsFinal(true);
		this.finales.add(listaEstados.get(i));
	}
	
	/**
	 * evaluate a string in the current automata
	 * @param entrada
	 * @return true if the automata accepts the input.
	 */
	public boolean evaluar(String entrada) {
		this.cadena = entrada;
		this.original = entrada;
		Estado actual = inicial;
		//System.err.println("Estado inicial " + actual.nombre);
		while (!this.cadena.isEmpty()) {
			//System.err.println("Estado actual " + actual.nombre );
			//System.err.println("cadena actual " + cadena);
			char toEval = cadena.charAt(0);
			this.cadena = cadena.substring(1);
			actual = evalChar(toEval, actual);
		}

		//System.err.println("Estado Final " + actual.nombre);
		//System.err.println("final? " + actual.estado_final);
		
		if (finales.contains(actual) || actual.estado_final)
			return true;
		else
			return false;
	}

	/**
	 * Similar a evaluar pero cartacter por caracter.
	 * @param toEval
	 * @param actual
	 * @return
	 */
	public Estado evalChar(char toEval, Estado actual) {
		Estado sig = actual.transiciones.get(toEval);
		if (sig == null)
			return atrapadoImplicito;
		else
			return sig;
	}

	/**
	 * Metodo factory que carga un automata definido en un archivo.
	 * @param ruta
	 * @return
	 * @throws Exception
	 */
	public static AutomataDinamico readFromFile(String ruta) throws Exception {
		List<String> lineas = Files.readAllLines(Paths.get(ruta));
		LinkedList<String> lectura = new LinkedList<>(lineas);

		AutomataDinamico generado = new AutomataDinamico();
		int simbolos = Integer.parseInt(lectura.removeFirst());

		for (int i = 0; i < simbolos; i++) {
			Character e = lectura.removeFirst().charAt(0);
			generado.alfabeto.add(e);
		}
		
		//System.err.println("Alfabeto: ");
		//generado.alfabeto.forEach(System.err::print);

		int nEstados = Integer.parseInt(lectura.removeFirst());
		//System.err.println("Estados : " + nEstados);
		
		HashSet<Integer> iFinales = new HashSet<>();

		String sFinales = lectura.removeFirst();
		Arrays.asList(sFinales.split(",")).stream().forEach(s -> iFinales.add(Integer.parseInt(s)));
		
		for (int i = 0; i < nEstados; i++) {
			boolean isFinal = iFinales.contains(i);
			Estado nuevo = new Estado(isFinal, "q" + i);
			generado.listaEstados.add(nuevo);
		}

		iFinales.stream().forEach(i -> {
			generado.finales.add(generado.listaEstados.get(i));
		});

		int iInicial = Integer.parseInt(lectura.removeFirst());
		generado.inicial = generado.listaEstados.get(iInicial);
		generado.inicial.setAsInicial(true);

		//System.err.println("inicial " + iInicial);
		
		int nTransiciones = Integer.parseInt(lectura.removeFirst());
		//System.err.println("Cantidad de transiciones : " + nTransiciones);
		IntStream.range(0, nTransiciones).forEach(n -> {
			String sTrasicion = lectura.removeFirst();
			//System.err.println("Transicion: " + sTrasicion);
			String[] ssTransicion = sTrasicion.split(" ");
			int origen = Integer.parseInt(ssTransicion[0]);
			char caracter = ssTransicion[1].charAt(0);
			int iDestino = Integer.parseInt(ssTransicion[2]);
			//System.err.println("estado " + origen + " caracter "+ caracter + "siguiente" + iDestino);
			Estado eDestino = generado.listaEstados.get(iDestino);
			generado.listaEstados.get(origen).transiciones.put(caracter, eDestino);
		});

		for (int i = 0; !lectura.isEmpty(); i++) {
			generado.listaEstados.get(i).nombre = lectura.removeFirst();
		}

		return generado;
	}

}
