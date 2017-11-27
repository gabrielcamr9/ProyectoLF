
import java.util.ArrayList;

import automatas.AutomataDinamico;
import protocolo.ValidadorEtherType;
import protocolo.ValidadorFormato;
import protocolo.ValidadorHeader1Q;
import protocolo.ValidadorPreambulo;

/**
 *	Esta clase nos permite simular un frame de ethernet que van llegando al equipo. 
 *
 */
public class Chain {
	private String value;
	private int Protocol;
	
	private String remaining;

	public Chain(String cadena) {
		this.value = cadena;
	}

	/**
	 * Funcion para evaluar si algun automata de Macs acepta una cadea,
	 * @param listaDeMacs
	 */
	public void evaluate(ArrayList<AutomataDinamico> listaDeMacs) {
		remaining = value;
		if (!checkChainFormat()) {
			System.out.println("El frame tiene caracteres ilegales");
			System.out.println("Se cancela transmision");
			return;
		}

		remaining = remaining.substring(8);
		if (!checkPreamble()) {
			System.out.println("El preambulo esta mal formado"); 
			System.out.println("Se cancela transmision");
			return; 
		}

		
		System.out.println("Se ha recibido el preambulo");

		boolean macsValidas = checkMacAddresses(listaDeMacs);
		remaining = remaining.substring(24);

		if (!macsValidas) {
			System.out.println("Alguna Mac fue invalida");
			System.out.println("Se cancela transmision");

			return;
		}

		Protocol = checkProtocol();

		switch (Protocol) {
		case (0):
			System.out.println("Protocol: 802.l");
			break;
		case (1):
			remaining = remaining.substring(4);
			System.out.println("Protocol: 802.lq");
			checkVlan();
			remaining = remaining.substring(4);
			break;
		case (2):
			System.out.println("Protocol: Invalid");
			return;
		}

		int len = checkEtherType();
		remaining = remaining.substring(4);
		
		if(!checkPayload(len)){
			System.out.println("La cantidad de datos no coincide");
			System.out.println("Se cancela transmision");

			return;
		}
		
		if(len == -1){
			remaining = remaining.substring(remaining.length() - 4 );
		}else{
			remaining = remaining.substring(len);
		}
		
		if(!checkCRC()){
			System.out.println("El CRC no es correcto");
			System.out.println("Se cancela transmision");

		}
		
	}

	
	//Si se tiene un frame 802.1q entonfce obtener la vlan
	private void checkVlan() {
		String vlan = remaining.substring(0,4);
		System.out.println("El frame esta identificado el tag de vlan " + vlan);
	}

	// Regresa true si la cadena no contiene caracteres fuera del alfabeto (0-F)
	// y es del tamaÃ±o adecuado. Regresa false de lo contrario
	private boolean checkChainFormat() {
		AutomataDinamico validorFormato = ValidadorFormato.buildAutomataForFormato();
		boolean res = validorFormato.evaluar(value) && value.length() > 40;
		return res;
	}

	// Regresa true si el preambulo es correcto y false de lo contrario
	private boolean checkPreamble() {
		AutomataDinamico validadorPreambulo = ValidadorPreambulo.buildAutomataForPreamble();
		return validadorPreambulo.evaluar(value.substring(0, 8));
	}

	// Imprime la MAC Address de destino y la MAC Address de origen
	// TambiÃ©n imprime si son validas. AKA estan dentro de la lista.
	private boolean checkMacAddresses(ArrayList<AutomataDinamico> listaDeMacs) {
		String macDestino = this.value.substring(8, 20);
		String macOrigen = this.value.substring(20, 32);
		boolean matchOrigen = false;
		boolean matchDestino = false;

		for (AutomataDinamico automata : listaDeMacs) {
			matchOrigen |= automata.evaluar(macOrigen);
		}

		for (AutomataDinamico automata : listaDeMacs) {
			matchDestino |= automata.evaluar(macDestino);
		}
		
		if(matchDestino){
			System.out.println("Se ha recibido correctamente una MAC de destino valida "+ macDestino );
		}else{
			System.out.println("La MAC de destino no esta registrada o es incorrecta "+  macDestino);
		}
		
		if(matchOrigen){
			System.out.println("Se ha recibido correctamente una MAC de origen válida "+ macOrigen );
		}else{
			System.out.println("La MAC de origen no esta registrada o es incorrecta "+  macOrigen);
		}
		
		return matchDestino && matchOrigen;

	}

	// Regresa 0 si la cadena peretence al protocolo 802.1
	// regresa 1 si peretenece al protocolo 802.1q y 2 si no pertenece a ninguno
	// de ellos
	private int checkProtocol() {
		AutomataDinamico automata = ValidadorHeader1Q.buildAutomataForHeader();
		String posibleHeader = this.value.substring(32, 36);
		System.out.println("Header leido " + posibleHeader);
		boolean unoQ = automata.evaluar(posibleHeader);
		if (unoQ)
			return 1;
		else
			return 0;
	}

	/**
	 * Intenta obtener el ethertype de un frame
	 * @return > 1 si se trata de un frame específco, 0 cualquier otro caso
	 */
	private int checkEtherType() {
		AutomataDinamico automata = ValidadorEtherType.buildAutomataForEtherType();
		String eval = this.remaining.substring(0,4);
		if(automata.evaluar(eval)){
			int size = Integer.parseInt(eval, 16); 
			System.out.println("El tamaño del frame es " + size);
			return size; 
		}else{
			
			String payloadProt =  ValidadorEtherType.Protocolos.get(eval);
			payloadProt = (payloadProt == null)? "Otro protocolo" : payloadProt;
			System.out.println("El ethertype del frame es del tipo: " + payloadProt);
			return -1;
		}
	}

	/**
	 * Comprueba el tamaño del payload
	 * @param len 
	 * @return wheter the payload is ok.
	 */
	private boolean checkPayload(int len) {
		if(len != -1 && remaining.length() - 4 != len ){
			System.out.println("El payload no tiene el tamaño indicado");
			return false;
		}
		if(Protocol == 0 &&  (remaining.length() < 46 || remaining.length() > 1500)){
			System.out.println("El payload no tiene el tamaño indicado");
			return false;
		}
		if(Protocol == 1 && (remaining.length() < 42 || remaining.length() > 1500)){
			System.out.println("El payload no tiene el tamaño indicado");
			return false;
		}
		System.out.println("Payload valido");
		return true;
	}

	/**
	 * Checks the length of the CRC
	 * @return true if it is present and woking
	 */
	private boolean checkCRC() {
		if(remaining.length() != 4){
			System.out.println("Hubo un problema al comprobar la cadena");
			return false;
		}else{
			System.out.println("Frame leido con exito");
			return true;
		}
	}

	private void checkInterFrameGap() {

	}

	@Override
	public String toString() {
		return ("Chain: " + value);
	}
}
