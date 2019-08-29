package worddao;

import java.io.IOException;
import java.nio.CharBuffer;
import java.rmi.RemoteException;
import java.util.List;
import java.sql.SQLException;

public class WordServiceImpl implements IWordService {
    
    private static String user = "sa";
    private static String password = "";
    private static String url = "jdbc:hsqldb:mem:.";
    private static WordDao database;
    
    public WordServiceImpl() throws SQLException {
        this.database = new WordDao(user, password, url);
    }
    
    @Override
    public void save(String hun, String eng) throws RemoteException {
        Word word = database.save(hun, eng);
        database.addWords(word);
    }

    @Override
    public List<String> lookup(String keyword) throws RemoteException {
        List<String> result = null;
        try{
            result = database.lookup(keyword);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> list() throws RemoteException {
        List<String> result = null;
        try{
            result = database.list();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }  
    
}
