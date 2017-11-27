package protocolo;

import automatas.AutomataDinamico;

public class ValidadorFormato {
	public static AutomataDinamico buildAutomataForFormato() {
		AutomataDinamico generado = null;
		try {
			generado = AutomataDinamico.readFromFile("./src/protocolo/formato.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return generado;
	}
}
