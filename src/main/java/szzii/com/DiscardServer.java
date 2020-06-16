package szzii.com;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szz
 */
public class DiscardServer {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("zs"));
        users.add(new User("ls"));
        users.add(new User("ww"));
        users.add(new User("l6"));
        List<String> zs = users.stream()
                .filter(user -> user.getName().equals("zs"))
                .map(User::getName)
                .collect(Collectors.toList());
        System.out.println(zs);
    }

}
