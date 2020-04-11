import biblioteka.Calendar;
import model.Thing;
import model.User;

import java.util.List;

public class Main
{
    public static void main(String[] args) {
        Calendar c = new Calendar();

        User user1 = new User("Jan", "Kowalski");
        User user2 = new User("John", "Smith");
        Thing thing1 = new Thing("Car");

        c.insertUser(user1);
        c.insertUser(user2);
        c.insertThing(thing1);
        c.getUsers();
        c.getThings();
        c.closeConnection();
    }
}


//        c.insertUser("Jan", "Kowalski");
//        c.insertThing("Car");
//
//
//
//        List<User> users = c.selectUser();
//
//        System.out.println("Lista uŻYDkowników: ");
//        for(User d: users)
//            System.out.println(d);
//
//        List<Thing> things = c.selectThing();
//
//        System.out.println("Lista rzeczy: ");
//        for(User e: users)
//            System.out.println(e);