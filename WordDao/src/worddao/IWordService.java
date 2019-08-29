package worddao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IWordService extends Remote{
    
    void save(String hun, String eng) throws RemoteException;
    List<String> lookup(String keyword)throws RemoteException;
    List<String> list() throws RemoteException ;
    
}
