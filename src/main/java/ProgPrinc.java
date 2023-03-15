import com.sun.source.util.DocTreePathScanner;

import java.util.Scanner;

public class ProgPrinc {

    public static void main(String[] args) {
        DemoCRUDPeUsers dbaccess = new DemoCRUDPeUsers();
        long id = -1;
        User u=null;
        while(true){
            Scanner sc = new Scanner(System.in);
            String username = sc.nextLine();
            String pwd = sc.nextLine();
             u = new User(username,pwd);

            id = dbaccess.login(u);

            u.setId(id);

            if(id!=-1)
                break;

        }

        // ura sunt logat , deci pot bagamancare
        while(true) {

            boolean isAdmin = dbaccess.isAdmin(u);
            if(!isAdmin) {

            System.out.print("Ia da mancare:");
            Scanner sc = new Scanner(System.in);
            String foodname = sc.nextLine();
            Food f = new Food(foodname);
            boolean success = dbaccess.insertFoodForUserID(f, id);
            if (success)
                System.out.println("food logged, next one");
            else
                System.out.println("tech problems, sorry ");
        }
            else
            {
                System.out.println("meniu de admin, ala cu creare user , tra la la ");
                while(true) {
                    System.out.println("1.add user ");
                    System.out.print("Add user:");
                    Scanner sc = new Scanner(System.in);
                    String username = sc.nextLine();
                    String pwd = sc.nextLine();
                    User newuser = new User(username,pwd);
                    dbaccess.insert(newuser);
                }
            }


        }



    }
}
