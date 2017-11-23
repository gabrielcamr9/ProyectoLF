package automatas;

import java.util.HashMap;

public class Estado {
	public HashMap<Character, Estado> transiciones;
	public boolean estado_final;
	public String nombre;
	public boolean estado_inicial;

	public Estado(String nombre){
		this.nombre = nombre;
		transiciones = new HashMap<>();
	}
	
	public void setAsInicial(boolean isInicial){
		this.estado_inicial = isInicial;
	}
	
	public void setAsFinal(boolean isFinal){
		this.estado_final = isFinal;
	}
	
	public Estado(boolean f, String nombre){
		this.estado_final = f;
		this.estado_inicial = false;
		this.nombre = nombre;
		transiciones = new HashMap<>();
	}
	
}
