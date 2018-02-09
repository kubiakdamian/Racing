package sample;

public class SQLRecordParser {

    public String createSaveQuery(String name, double time) {
        String query = "";
        query = "INSERT INTO rekordy VALUES (NULL, '"+ name + "', '" + time +"');";
        return query;
    }

    public String createGetRecordsQuery() {
        String query = "";
        query = "SELECT * FROM rekordy ORDER BY czas LIMIT 10";
        return query;
    }
}
