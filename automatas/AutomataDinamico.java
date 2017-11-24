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

public class AutomataDinamico {
	public ArrayList<Estado> listaEstados;
	public HashSet<Character> alfabeto;
	public HashSet<Estado> finales;
	public Estado inicial;
	private String cadena;

	private Estado atrapadoImplicito;

	public AutomataDinamico() {
		listaEstados = new ArrayList<>();
		alfabeto = new HashSet<>();
		finales = new HashSet<>();
		cadena = null;
		atrapadoImplicito = new Estado("atrapado");
	}

	public boolean evaluar(String entrada) {
		this.cadena = entrada;
		Estado actual = inicial;
		System.err.println("Estado inicial " + actual.nombre);
		while (!this.cadena.isEmpty()) {
			System.err.println("Estado actual " + actual.nombre );
			System.err.println("cadena actual " + cadena);
			char toEval = cadena.charAt(0);
			this.cadena = cadena.substring(1);
			actual = evalChar(toEval, actual);
		}

		System.err.println("Estado Final " + actual.nombre);
		System.err.println("final? " + actual.estado_final);
		
		if (finales.contains(actual))
			return true;
		else
			return false;
	}

	public Estado evalChar(char toEval, Estado actual) {
		Estado sig = actual.transiciones.get(toEval);
		if (sig == null)
			return atrapadoImplicito;
		else
			return sig;
	}

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

		System.err.println("inicial " + iInicial);
		
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
