package protocolo;

import java.util.stream.IntStream;

import automatas.AutomataDinamico;

public class ValidadorPreambulo {
	public static AutomataDinamico buildAutomataForPreamble(){
		AutomataDinamico generado = null; 
		try {
			generado = AutomataDinamico.readFromFile("./src/protocolo/preambulo.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generado;
	}
}
