
import java.util.Scanner;

import protocolo.ValidadorMac;

public class InterfazDeUsuario {

    private Scanner reader;
    private ChainHandler ah;

    public InterfazDeUsuario(Scanner reader) {
        this.reader = reader;
        ah = new ChainHandler();
    }

    //Este método se encarga de manejar la lógica detrás de la interfaz de usuario
    public void start() throws Exception {
        outerloop:
        while (true) {
            printMenu();
            switch (reader.nextLine()) {
                case ("1"):
                    printAbout();
                    break;
                case ("2"):
                    printInstructions();
                    break;
                case ("3"):
                    insertMacs();
                    break;
                case ("4"):
                    insertChains();
                    break;
                case ("5"):
                    break outerloop;
            }
        }
    }

    public void printMenu() {
        System.out.println("Welcome");
        System.out.println("1.- About");
        System.out.println("2.- Instructions");
        System.out.println("3.- Insert MACs");
        System.out.println("4.- Evaluate Chains");
        System.out.println("5.- Exit");
    }

    public void printAbout() {
        System.out.println("Virtual Switch");
        System.out.println("Software Version: (1.0)");
        System.out.println("Authors:");
        System.out.println("Gabriel Campollo Ramírez");
        System.out.println("Damián García Serrano");
        System.out.println("Carlos González ");
        System.out.println("");
        System.out.println("Source code repo");
        System.out.println("https://github.com/gabrielcamr9/ProyectoLF");
        System.out.println("Press any key to continue");
        reader.nextLine();
    }

    public void printInstructions() {
        System.out.println("FOR INSERT MAC");
        System.out.println("MAC addresses need to be in Hexadecimal format,");
        System.out.println("if they are not in the correct format they will be ignored.");
        System.out.println("Send a -1 to back out");
        System.out.println("FOR EVALUATE CHAINS");
        System.out.println("Send all the chains you want to be evaluated in order.");
        System.out.println("Chains need to be sent in Hexadecimal format");
        System.out.println("Send a -1 to finish");
        System.out.println("Press any key to continue");
        reader.nextLine();
    }

    public void insertMacs() throws Exception {
        System.out.println("Ingresa las macs");
        String mac = "";
        while (!mac.equals("-1")) {
            System.out.println("Ingresa la MAC, o -1 para salir");

        	mac = reader.nextLine();
        	
        	if(mac.equals("-1"))
        		break;
        	
        	if(ValidadorMac.isValidMac(mac))
        		ah.addMac(mac);
        	else
        		System.out.println("Esa mac no fue valida, comprueba el formato");
        }
    }

    public void insertChains() {
        String chain = "*";
        while (!chain.equals("-1")) {
        	System.out.println("Ingresa las cadenas hexadecimales a validar");
            chain = reader.nextLine();
            if(!chain.equals("-1"))
            	ah.addChain(chain);
        }
        ah.evaluateAll();
    }

}
