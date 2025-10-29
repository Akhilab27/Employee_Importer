import java.sql.*;      // JDBC classes: Connection, Statement, PreparedStatement, ResultSet
import java.io.*;       // File reading: BufferedReader, FileReader

public class EmployeeImporter {

    // The SQLite database file (created in project folder)
    private static final String DB_URL = "jdbc:sqlite:employees.db";

    public static void main(String[] args) {
         String csvFile = "data/Employees.csv";  // relative path to CSV file
    try {
        //  Add this line BEFORE getConnection()
        Class.forName("org.sqlite.JDBC");

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            createTable(conn);          // ensure table exists
            importCSV(conn, csvFile);   // read CSV and insert rows
            showAll(conn);              // print rows to verify
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    // Create the employees table if it doesn't exist
    private static void createTable(Connection c) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS employees(
                id INTEGER PRIMARY KEY,
                name TEXT,
                department TEXT,
                salary REAL
            )
            """;
        try (Statement s = c.createStatement()) {
            s.execute(sql);
        }
    }

    // Import CSV rows into the employees table
    private static void importCSV(Connection c, String file) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file));
             PreparedStatement ps = c.prepareStatement(
                 "INSERT OR REPLACE INTO employees VALUES (?,?,?,?)")) {

            br.readLine();  // skip header line "id,name,department,salary"
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");         // simple split for basic CSVs
                ps.setInt(1, Integer.parseInt(t[0].trim()));   // id
                ps.setString(2, t[1].trim());                  // name
                ps.setString(3, t[2].trim());                  // department
                ps.setDouble(4, Double.parseDouble(t[3].trim())); // salary
                ps.executeUpdate(); // run the INSERT
            }
        }
    }

    // Print all rows in employees table to console
    private static void showAll(Connection c) throws SQLException {
        try (ResultSet rs = c.createStatement()
                             .executeQuery("SELECT * FROM employees")) {
            System.out.println("All employees:");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary"));
            }
        }
    }
}
