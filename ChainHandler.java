
import java.util.ArrayList;

import automatas.AutomataDinamico;
import protocolo.ValidadorMac;


/**
 * Clase para almacenar las cadenas y las MACS
 *
 */
public class ChainHandler {
    private ArrayList<AutomataDinamico> listaDeMACs;
    private ArrayList<Chain> listaDeCadenas; 
    public ChainHandler(){
        listaDeMACs = new ArrayList<>();
        listaDeCadenas = new ArrayList<>();
    }
    
    public void addMac(String mac){
    	String new_mac = mac.trim().replaceAll(":", "").toUpperCase();
    	AutomataDinamico a = ValidadorMac.buildAutomataForMac(new_mac);
        listaDeMACs.add(a);
    }
    
    public void addChain(String chain){
    	listaDeCadenas.add(new Chain(chain));
    }
    
    public void evaluateAll(){
        for(Chain c : listaDeCadenas){
            System.out.println(c);
            try{
            	c.evaluate(listaDeMACs);
            }catch(IndexOutOfBoundsException e){
            	System.out.println("La trama ingresada es muy corta");
            }
            catch (Exception e) {
				System.out.println("Hay un problema con la trama leida");
				e.printStackTrace();
			}
        }
    }
}
