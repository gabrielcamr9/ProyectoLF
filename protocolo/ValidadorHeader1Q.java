package protocolo;

import automatas.AutomataDinamico;

public class ValidadorHeader1Q {
	public static AutomataDinamico buildAutomataForHeader(){
		AutomataDinamico generado = null; 
		try {
			generado = AutomataDinamico.readFromFile("./src/protocolo/802.1Q_TPID.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generado;
	}
}
