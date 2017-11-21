
import java.util.Scanner;

public class InterfazDeUsuario {

    private Scanner reader;
    private ChainHandler ah;

    public InterfazDeUsuario(Scanner reader) {
        this.reader = reader;
        ah = new ChainHandler();
    }

    //Este método se encarga de manejar la lógica detrás de la interfaz de usuario
    public void start() {
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

    public void insertMacs() {
        String mac = reader.nextLine();
        while (!mac.equals("-1")) {
            ah.addMac(mac);
            mac = reader.nextLine();
        }
    }

    public void insertChains() {
        String chain = reader.nextLine();
        while (!chain.equals("-1")) {
            ah.addChain(chain);
            chain = reader.nextLine();
        }
        ah.evaluateAll();
    }

}
