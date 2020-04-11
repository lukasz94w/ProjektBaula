package biblioteka;
import model.Thing;
import model.User;
import java.sql.*;
//https://www.postgresqltutorial.com/postgresql-jdbc/ - fajny tutorial, sa tam tez metody do update'u czy kasowania ale narazie niepotrzebne
//https://www.postgresqltutorial.com/postgresql-jdbc/query/ - na podstawie tego wyszukiwanie Userow zrobione
//https://www.postgresqltutorial.com/postgresql-jdbc/insert/ - na podstawie tego dodawanie Userow zrobione

public class Calendar {

    private static String URL = "jdbc:postgresql://localhost:8012/event_base";
    private static String USER = "postgres";
    private static String PASSWORD = "password";

    private Connection conn;
    private Statement stat;

    public Calendar() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stat = conn.createStatement();
            System.out.println("Connected with database");
        } catch (SQLException e) {
            System.err.println("Problem during connection");
            e.printStackTrace();
        }
        createTables();
    }

    public boolean createTables()
    {
        String createUsers = "CREATE TABLE IF NOT EXISTS users(user_id serial primary key, name varchar, surname varchar)";
        //String createUsers = "CREATE TABLE IF NOT EXISTS users(name varchar, surname varchar)";
        String createThings = "CREATE TABLE IF NOT EXISTS things(thing_id serial primary key, name varchar)";

        try {
            stat.execute(createUsers);
            stat.execute(createThings);
        } catch (SQLException e) {
            System.err.println("Error during creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }

        public long insertUser(User user) {

            String SQL = "INSERT INTO users(name, surname) "
                    + "VALUES(?,?)";

            long id = 0;

            try {PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getSurname());

                int affectedRows = pstmt.executeUpdate();
                // check the affected rows
                if (affectedRows > 0) {
                    // get the ID back
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                        }

            }catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return id;
    }

    public void getUsers() {
        String SQL = "SELECT user_id, name, surname FROM users";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display user information
            displayUser(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void displayUser(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("user_id") + "\t"
                    + rs.getString("name") + " "
                    + rs.getString("surname"));
        }
    }


    public long insertThing(Thing thing) {

        String SQL = "INSERT INTO things(name) "
                + "VALUES(?)";

        long id = 0;

        try {PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, thing.getName());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }

                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public void getThings() {
        String SQL = "SELECT thing_id, name FROM things";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display thing information
            displayThing(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void displayThing(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("thing_id") + "\t"
                    + rs.getString("name") + " ");
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error during closing connection");
            e.printStackTrace();
        }
    }
}


//    public boolean insertUser(String name, String surname) {
//        try {
//            PreparedStatement prepStmt = conn.prepareStatement(
//                    "insert into users values ((SELECT MAX (user_id)+1 FROM users), ?, ?);");
//            prepStmt.setString(1, name);
//            prepStmt.setString(2, surname);
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.err.println("Błąd przy wstawianiu użytkownika");
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

//    public List<User> selectUser() {
//        List<User> users = new LinkedList<User>();
//        try {
//            ResultSet result = stat.executeQuery("SELECT * FROM users");
//            int user_id;
//            String name, surname;
//            while(result.next()) {
//                user_id = result.getInt("user_id");
//                name = result.getString("name");
//                surname = result.getString("surname");
//                users.add(new User(user_id, name, surname));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return users;
//    }

//    public boolean insertThing(String name) {
//        try {
//            PreparedStatement prepStmt = conn.prepareStatement(
//                    "insert into things values ((SELECT MAX (thing_id)+1 FROM things), ?);");
//            prepStmt.setString(1, name);
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.err.println("Błąd przy wstawianiu rzeczy");
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public List<Thing> selectThing() {
//        List<Thing> things = new LinkedList<Thing>();
//        try {
//            ResultSet result = stat.executeQuery("SELECT * FROM things");
//            int thing_id;
//            String name;
//            while(result.next()) {
//                thing_id = result.getInt("thing_id");
//                name = result.getString("name");
//                things.add(new Thing(thing_id, name));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return things;
//    }