import java.sql.SQLException;

public class Main {

    //SPECIFY THE DATABASE CREDENTIALS HERE
    private static String databaseName = "test";
    private static String username = "root";
    private static String password = "";

    public static void main(String[] args) {
        try {
            Database database = new Database(databaseName, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to the database");
            System.exit(1);
        }
    }
}
