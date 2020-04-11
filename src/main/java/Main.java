import biblioteka.Calendar;
import model.Thing;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) {
        Calendar c = new Calendar();

        User user1 = new User("Jan", "Kowalski");
        User user2 = new User("John", "Smith");
        Thing thing1 = new Thing("Car");

        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(new User("Johan", "Schmidt"));
        usersList.add(new User("Enrico", "Palazzo"));

        c.insertUsers(usersList);

        ArrayList<Thing> thingArrayList = new ArrayList<>();
        thingArrayList.add(new Thing("Plane"));
        c.insertThings(thingArrayList);

        c.insertUser(user1);
        c.insertUser(user2);
        c.insertThing(thing1);
        c.getUsers();
        c.getThings();
        c.closeConnection();
    }
}