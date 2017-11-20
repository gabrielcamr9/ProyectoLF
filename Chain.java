
import java.util.ArrayList;


public class Chain {
    private String value;
    private ArrayList<String> listaDeMacs;
    private int Protocol;
    
    public Chain(String cadena, ArrayList<String> listaDeMacs){
        this.value = cadena;
        this.listaDeMacs = listaDeMacs;
    }
    
    
    public void evaluate(){
        if(!checkChainFormat()){
            System.out.println("The chain either contains wrong characters or is of an incorrect size");
            return;
        }
        if(!checkPreamble()){
            System.out.println("The chain preamble is wrong"); //No se que verga sea preambulo ni que pase cuando es incorrecto
            return;                                             //modifiquen lo que pasa cuando está mal si es necesario
        }                                                       
        checkMacAddresses();
        
        Protocol = checkProtocol();
        switch(Protocol){
           case(0):
               System.out.println("Protocol: 802.l");
           case(1):
               System.out.println("Protocol: 802.lq");
           case(2):
               System.out.println("Protocol: Invalid");
        }
        
        //Aqui hace faltan que se llamen los metodos para checar el resto de las partes de la cadena
    }
    
    //Regresa true si la cadena no contiene caracteres fuera del alfabeto (0-H)
    //y es del tamaño adecuado. Regresa false de lo contrario
    private boolean checkChainFormat(){
        return true;
    }
    //Regresa true si el preambulo es correcto y false de lo contrario
    private boolean checkPreamble(){
        return true;
    }
    
    //Imprime la MAC Address de destino y la MAC Address de origen
    //También imprime si son validas. AKA estan dentro de la lista.
    private void checkMacAddresses(){
        
    }
    
    //Regresa 0 si la cadena peretence al protocolo 802.1 
    //regresa 1 si peretenece al protocolo 802.1q y 2 si no pertenece a ninguno de ellos
    private int checkProtocol(){
        return 0;
    }
    
    private void checkEtherType(){
        
    }

    private void checkPayload(){
        
    }
    
    private void checkCRC(){
        
    }
    
    private void checkInterFrameGap(){
        
    }

    
    
    @Override
    public String toString(){
        return("Chain: " + value);
    }
}
