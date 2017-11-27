package protocolo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import automatas.AutomataDinamico;

public class ValidadorEtherType {
	public static HashMap<String, String> Protocolos;
	
	static{
		Protocolos = new HashMap<>();
		List<String> lineas = null;
		try {
			lineas = Files.readAllLines(Paths.get("./src/protocolo/protocolosList"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lineas.stream().forEach(s -> {
			String[] arr = s.split("\t");
			
			String hex = arr[0];
			
			String protName = arr[1];
			Protocolos.put(hex, protName);
		});
	}
	
	public static AutomataDinamico buildAutomataForEtherType(){
		AutomataDinamico generado = null; 
		try {
			generado = AutomataDinamico.readFromFile("./src/protocolo/EtherType.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generado;
	}
	
}
