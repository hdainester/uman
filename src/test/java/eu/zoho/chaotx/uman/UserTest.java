package eu.zoho.chaotx.uman;

import java.util.Scanner;

public class UserTest {
    public void testManager() {
        // UserManager uman = new UserManager(System.getenv("APPDATA") + "/bn_manager/user.data");
        // String name, password, key;
        // boolean running = true;
        
        // while(running) {
        //     Scanner sc = new Scanner(System.in);
        //     System.out.println("------------------------------------------------------------------");
        //     System.out.print("Select [register:r | login:l | save:s | load:d | show:a | exit:e]: ");
            
        //     switch(sc.next()) {
        //         case "r":
        //             System.out.print("Name: ");
        //             name = sc.next();
        //             System.out.print("Password: ");
        //             password = sc.next() + sc.nextLine();
        //             System.out.print("Key: ");
        //             key = sc.next() + sc.nextLine();

        //             try {
        //                 User u = new User(name, password, 16);
        //                 u.setData("api_key", key, password);
        //                 uman.addUser(u);
        //                 System.out.println("=> user '" + name + "' registered");
        //             } catch(IllegalArgumentException e) {
        //                 System.out.println("=> error: |password| may not be greater than |key|");
        //             }

        //             break;
        //         case "l":
        //             System.out.print("Name: ");
        //             name = sc.next();
        //             System.out.print("Password: ");
        //             password = sc.next() + sc.nextLine();

        //             if(uman.getUserMap().containsKey(name)) {
        //                 User u = uman.getUserMap().get(name);
        //                 if(u.isValid(password))
        //                     System.out.printf("=> login succeeded: id=%-15s key=%-15s\n",
        //                         name, u.getData("api_key", password));
        //                 else
        //                     System.out.println("=> login failed: invalid password");
        //             } else
        //                 System.out.println("=> no such user '" + name + "'");
        //             break;
        //         case "s":
        //             uman.save();
        //             System.out.println("=> saved to file");
        //             break;
        //         case "d":
        //             uman.load();
        //             System.out.println("=> loaded from file");
        //             break;
        //         case "a":
        //             System.out.println("=> registered users:");
        //             uman.getUserMap().values().forEach(user ->
        //                 System.out.printf("\t- id=%-10s token=%-15s\n",
        //                     user.getUid(), user.getToken()));
        //             break;
        //         case "e":
        //             System.out.println("=> exited");
        //             running = false;
        //             sc.close();
        //             break;
        //         default:
        //             System.out.println("=> invalid mode");
        //             break;
        //     }
        // }
    }
}