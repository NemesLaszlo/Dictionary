package worddao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordDao implements AutoCloseable{
    
    private static String user = "sa";
    private static String password = "";
    private static String url = "jdbc:hsqldb:mem:.";
    private static Connection conn;


    /*public static void main(String[] args) throws SQLException, Exception {
        
        WordDao wordDao = new WordDao(user, password, url);
        /*Scanner sc = new Scanner(System.in);
        String line;
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            String[] str = line.split(" ");
            wordDao.addWords(wordDao.save(str[0], str[1]));
            System.out.println("--------------------------");
            wordDao.printWords();
            
            if(str[0].equals("list")){
                for(String s: wordDao.list()){
                    System.out.print(s + " ");
                }
            }
        }
        /*for(String s: wordDao.lookup("au")){
            System.out.println(s + " ");
        }
        wordDao.close();
    }*/

    public WordDao(String user,String password,String url) throws SQLException {
        this.conn = DriverManager.getConnection(url,user,password);
        try {
                createTables(conn);
                addWords(new Word("auto","car"));
                addWords(new Word("labda","ball"));
                //printWords();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    @Override
    public void close() throws Exception {
        conn.close();
    }
    
    public Word save(String hunword,String engword) {
        Word c = new Word(hunword, engword);
        return c;
    }
    
    private void createTables(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement()) {
            //st.executeUpdate("DROP TABLE IF EXISTS dictionary;");
            st.executeUpdate("CREATE TABLE dictionary (hunword VARCHAR(80) PRIMARY KEY, engword VARCHAR(80));");
            System.out.println("Tables created");
        }
    }
    
    public void addWords(Word word) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO dictionary (hunword, engword) VALUES (?, ?);")) {
            st.setString(1, word.getHunWord());
            st.setString(2, word.getEngWord());
            st.addBatch();
            st.executeBatch();
        }catch(SQLException ex) {
            System.out.println("Already Exist!");
        }
    }
    
    public void printWords() throws SQLException {
        try(Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from dictionary;")) {
            while(rs.next()) {
                final String hunword = rs.getString("hunword");
                final String engword = rs.getString("engword");
                System.out.println("hunword: " + hunword + ", engword: " + engword);
            }
        }
    }
    
    public List<String> list() throws SQLException {
        List<String> result = new ArrayList<>();
        try(Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from dictionary;")) {
            while(rs.next()) {
                final String hunword = rs.getString("hunword");
                final String engword = rs.getString("engword");
                result.add(hunword);
                result.add(engword);
            }
        }
        return result;
    }
    
    public List<String> lookup(String parameter) throws SQLException {
        List<String> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("SELECT * from dictionary WHERE hunword LIKE ? OR engword LIKE ?;");
        st.setString(1, '%'+parameter+'%');
        st.setString(2, '%'+parameter+'%');
        ResultSet rs = st.executeQuery();
        try{
            while(rs.next()) {
                final String hunword = rs.getString("hunword");
                final String engword = rs.getString("engword");
                result.add(hunword);
                result.add(engword);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
}
