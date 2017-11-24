package protocolo;

import java.util.ArrayList;
import java.util.stream.IntStream;

import automatas.AutomataDinamico;
import automatas.Estado;

public class ValidadorMac {
	
	public static boolean isValidMac(String mac) throws Exception{
		
		boolean res =  true;
		for(int i = 0 ; i< 5; i++){
			AutomataDinamico a1 = AutomataDinamico.readFromFile("./mac1.txt");
			String chunk = mac.substring(0, 3);
			//System.err.println("evaluando "+ chunk);
			mac = mac.substring(3);
			res &= a1.evaluar(chunk);
			
			if(!res)
				break;
		}
		
		AutomataDinamico a2 = AutomataDinamico.readFromFile("./mac2.txt");
		
		//System.err.println("Evaluado " + mac);
		if(res){
			res &= a2.evaluar(mac);
		}
		
		return res; 
		
	}
	
	public static AutomataDinamico buildAutomataForMac(String mac){
		AutomataDinamico generado = new AutomataDinamico();
		String macUpper =  mac.toUpperCase().replaceAll(":", "");
		macUpper.chars().forEach(c -> generado.alfabeto.add((char) c));
		
		generado.listaEstados.add(new Estado("q0"));
		macUpper.chars().forEach(c -> {
			System.err.println("Creando estado q" + generado.listaEstados.size());
			Estado e = new Estado("q"+ generado.listaEstados.size());
			generado.listaEstados.add(e);
		});
		
		IntStream.range(0, 12).forEach(i -> {
			Estado e = generado.listaEstados.get(i);
			Estado sig = generado.listaEstados.get(i+1);
			e.transiciones.put(macUpper.charAt(i), sig);
		});

		
		generado.listaEstados.get(0).estado_inicial = true;
		generado.listaEstados.get(12).estado_final = true;
		
		generado.inicial = generado.listaEstados.get(0);
		generado.finales.add(generado.listaEstados.get(12));
		
		return generado;
		
	}

	public static boolean isKnownIP(ArrayList<AutomataDinamico> conocidas, String mac){
		boolean res = conocidas.stream().anyMatch(a -> a.evaluar(mac));
		return res;
	}
}
