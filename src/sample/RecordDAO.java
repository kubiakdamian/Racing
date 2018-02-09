package sample;

import java.sql.*;

public class RecordDAO {

    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/mydb";
    private final static String DBUSER = "root";
    private final static String DBPASS = "";
    private final static String DBDRIVER = "com.mysql.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private String query;
    private SQLRecordParser sqlRecordParser;

    public RecordDAO() {
        sqlRecordParser = new SQLRecordParser();
    }

    public void save(String name, double time) {
        query = sqlRecordParser.createSaveQuery(name, time);
        sendQuery(query, false);
    }

    public void getRecords() {
        query = sqlRecordParser.createGetRecordsQuery();
        sendQuery(query, true);
    }

    private void sendQuery(String query, boolean isGettingRecords){
        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            if(isGettingRecords == true){
                ResultSet rs = statement.executeQuery(query);
                while(rs.next()){
                    double time  = rs.getDouble("czas");
                    String name = rs.getString("Uzytkownik");

                    System.out.println(name + "  " + time);
                }
                rs.close();
            }else{
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}