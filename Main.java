
import java.util.Scanner;

import automatas.AutomataDinamico;
import protocolo.ValidadorMac;
import protocolo.ValidadorPreambulo;


public class Main {
    public static void main(String[] args) {
        InterfazDeUsuario I = new InterfazDeUsuario(new Scanner(System.in));
        try {
			I.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main2(String [] args){
    	String mac = "32:18:b0:c5:73:34";
    	mac = mac.toUpperCase();

    	boolean isValidMac = false;


		AutomataDinamico ad = ValidadorMac.buildAutomataForMac("32:18:b0:c5:73:34");
		
		System.err.println("Mac eval " + ad.evaluar(mac.replaceAll(":", ""))); 
		System.err.println("Mac eval " + ad.evaluar("3218b0c57335".toUpperCase())); 		

    }
}
