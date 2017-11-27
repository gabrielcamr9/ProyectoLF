package automatas;

import java.util.HashMap;
import java.util.function.Function;

public class Estado {
	public HashMap<Character, Estado> transiciones;
	public boolean estado_final;
	public String nombre;
	public boolean estado_inicial;
	Function<String, Void> accion;
	
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
	
	public void setAction(Function<String, Void> f){
		this.accion = f;
	}
	
	public void executeAction( String cadena ){
		if( this.accion != null)
			this.accion.apply(cadena);
	}
}
