
import java.util.ArrayList;

public class ChainHandler {
    private ArrayList<String> listaDeMACs;
    private ArrayList<Chain> listaDeCadenas; 
    public ChainHandler(){
        listaDeMACs = new ArrayList<>();
    }
    
    public void addMac(String mac){
        listaDeMACs.add(mac);
    }
    
    public void addChain(String chain){
        listaDeCadenas.add(new Chain(chain, listaDeMACs));
    }
    
    public void evaluateAll(){
        for(Chain c : listaDeCadenas){
            System.out.println(c);
            c.evaluate();
        }
    }
}
