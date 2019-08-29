package worddao;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WordServiceRunner {
        
    public static void main(String[] argv) {
         
        int port = 2009;

        if(argv.length >= 1 && argv[0].equals("--with-registry")) {
            try {
                if(argv.length >= 2) {
                    port = Integer.parseInt(argv[1]);
                }
                java.rmi.registry.LocateRegistry.createRegistry(port);
                System.out.println("RMI registry ready.");
            } catch (Exception e) {
                System.out.println("Exception starting RMI registry:");
                e.printStackTrace();
                return;
            }
        } else if (argv.length >= 1) {
            port = Integer.parseInt(argv[0]);
        }

        System.out.println("Using port: " + port);

        try {

            String name = "Words";
            IWordService engine = new WordServiceImpl();
            IWordService stub =
                    (IWordService) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry(port);
            registry.rebind(name, stub);
            System.out.println("Szolgaltatas bound");
        } catch (Exception e) {
            System.err.println("Szolgaltatas exception:");
            e.printStackTrace();
        }
         
     }
}
