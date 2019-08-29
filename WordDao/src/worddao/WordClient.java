package worddao;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.List;

public class WordClient {
    
    public static void main(String[] args) {
        try {
            int port = args.length >= 1 ? Integer.parseInt(args[0]) : 2009;
            String name = "Words";
            Registry registry = LocateRegistry.getRegistry(port);
            IWordService comp = (IWordService) registry.lookup(name);
            Scanner sc = new Scanner(System.in);
            String line;
            while(sc.hasNextLine()) {
                line = sc.nextLine();
                String[] str = line.split(" ");
                switch(str[0]) {
                    case "add":
                        comp.save(str[1], str[2]);
                        break;
                    case "lookup":
                        List<String> resultLookup = comp.lookup(str[1]);
                        for(String x : resultLookup) {
                            System.out.print(x + " ");
                        }
                        break;
                    case "list":
                        List<String> resultList = comp.list();
                        for(String x : resultList) {
                            System.out.print(x + " ");
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
