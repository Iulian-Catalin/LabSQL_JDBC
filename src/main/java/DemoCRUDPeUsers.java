import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemoCRUDPeUsers {

    public static void main(String[] args) throws SQLException {

        // cum fac CRUD pe un obiect de tip User

        DemoCRUDPeUsers obiect = new DemoCRUDPeUsers();

        List result = obiect.readAllUsers();
      System.out.println(result);

////        User u = new User("costica", "password1");
////
////        boolean isAdded = obiect.insert(u);
////        System.out.println(isAdded);
//
//           List result = obiect.readAllUsersV2();
//           System.out.println(result);


   //     User u = new User("ionicaddf", "anaarepere1");
//      boolean ex = obiect.update(u);
//
//        System.out.println(ex);



//        String status = obiect.delete(u);
//
//        if(status.equals("0"))
//            System.out.println("stergerea nu se poate pt ca userul nu exista");
//        else
//            if(status.equals("1"))
//                System.out.println("stergerea s-a facut cu succes ");
//            else
//                System.out.println(status);



//        List result = obiect.readFoodOfAUser("ion");
//           System.out.println(result);

//        int id = 2;
//        Food f = new Food("clatite cu gem de avocado");
//        boolean result = obiect.insertFoodForUserID(f,id);
//        System.out.println(result);
    }

     boolean insert (User u)  {

        // COD CARE SCRIE IN DB



        // daca are rezultate, citirea lor


        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
         int val = 0; // 1
         try {
             Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

             // rulare sql
             PreparedStatement pSt = conn.prepareStatement("insert into users(username, password) values(?, ?)");
             pSt.setString(1, u.getUsername());
             pSt.setString(2, u.getPassword());
             val = pSt.executeUpdate();
         } catch (SQLException e) {
            e.printStackTrace();
         }
         boolean ok = false;
        if(val!=0)
           ok=true;
        return ok;
    }

    private List<User> readAllUsers() throws SQLException {
        List<User> lu = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor



        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        Statement pSt = conn.createStatement();

        ResultSet rs = pSt.executeQuery("select * from users order by username asc");



        while(rs.next()) {

            String user = rs.getString("username").trim();
            String p = rs.getString("password").trim();
            long id  = rs.getLong("id");
            User u = new User(user,p);
            u.setId(id);
            lu.add(u);

        }
        return lu;
    }


    public List<User> readAllUsersV2() throws SQLException {
        List<User> lu = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor



        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        PreparedStatement pSt = conn.prepareStatement("select * from users where username='ion' order by username asc ");

        // pSt.set...

        ResultSet rs = pSt.executeQuery();



        while(rs.next()) {

            String user = rs.getString("username").trim();
            String p = rs.getString("password").trim();
            User u = new User(user,p);
            lu.add(u);

        }
        return lu;
    }

     boolean update (User u) throws SQLException {

        // COD CARE SCRIE IN DB



        // daca are rezultate, citirea lor


        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        PreparedStatement pSt = conn.prepareStatement("update users set password= ? where username = ?");
        pSt.setString(1, u.getPassword());
        pSt.setString(2, u.getUsername());
        int val = pSt.executeUpdate(); // 1

        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }

    private String delete(User u) {
        System.out.println("intra");

        String messsage=null;
        //conectare la DB cu incarcare driver

        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB = "postgres";
        final String PWDB = "123434yuetyeu45u7iu4w56uyt";

        int val = 0; // 1 pt inserare ok sau 0 pt operatie nereusita
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDB);

            //rulare sql

            PreparedStatement pst = conn.prepareStatement("delete from users where username=?");
            pst.setString(1, u.getUsername());
            val = pst.executeUpdate();
            messsage=String.valueOf(val);
        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
         if(m.contains("does not exist"))
             messsage="There must be a connection problem. Ourt team wilsdghsft her hrt h reg";
         else
             if (m.contains("violates foreign key constraint"))
                 messsage="You cannot delete this user becasue there is food registredgdfgn ret";
            else
                 messsage="General error. Our team will ... " ;

        }
        return messsage;
    }


    /*
    select username as u,foodname as f from loggedfood,users
where users.username='ion'
and users.id=loggedfood.iduser
     */
    public List<Food> readFoodOfAUser(String username) throws SQLException {
        List<Food> lf = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor



        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        PreparedStatement pSt = conn.prepareStatement("select  foodname from loggedfood,users where users.username=? and users.id=loggedfood.iduser");

         pSt.setString(1, username);

        ResultSet rs = pSt.executeQuery();



        while(rs.next()) {
            String foodname = rs.getString("foodname").trim();
            Food u = new Food(foodname);
            lf.add(u);

        }
        return lf;
    }


    boolean insertFoodForUserID (Food f, long id)  {

        // COD CARE SCRIE IN DB



        // daca are rezultate, citirea lor


        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        int val = 0; // 1
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("insert into loggedfood (foodname,iduser) values (?,?);");
            pSt.setString(1, f.getFoodname());
            pSt.setLong(2, id);
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }


    long login (User u)  {

        // -1 daca nu exista , si id-ul usaerului daca exista


        long id = -1;

        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("select id from users where username=? and password=? ");

            pSt.setString(1,u.getUsername());
            pSt.setString(2,u.getPassword());
            ResultSet rs = pSt.executeQuery();


            while(rs.next()) {

                id = rs.getLong("id");
                 return id;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return id;

    }


    boolean isAdmin (User u)  {

        // -1 daca nu exista , si id-ul usaerului daca exista


        boolean isAdmin=false;

        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("select isadmin from users where username=? and password=? ");

            pSt.setString(1,u.getUsername());
            pSt.setString(2,u.getPassword());
            ResultSet rs = pSt.executeQuery();


            while(rs.next()) {

                isAdmin = rs.getBoolean("isadmin");
                return isAdmin;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return isAdmin;

    }

}