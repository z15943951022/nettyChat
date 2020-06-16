package szzii.com.MyStream;

import szzii.com.MySpliterator;
import szzii.com.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author szz
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("zs"));
        users.add(new User("ls"));
        users.add(new User("ww"));
        users.add(new User("l6"));
        MySpliterator mySpliterator = new MySpliterator(users);
        ZStream<User> stream = StreamSupport.stream(mySpliterator)
                .filter(u -> Objects.equals(u.getName(),"zs"));

    }
}
