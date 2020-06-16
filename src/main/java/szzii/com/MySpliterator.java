package szzii.com;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author szz
 */
public class MySpliterator implements Spliterator.OfPrimitive<User,Consumer,MySpliterator> {

    private List<User> users;

    private int startIndex;

    private int ednIndex;

    private int characteristics;

    public MySpliterator(List<User> users) {
        this(users,0,users.size() - 1,Spliterator.NONNULL);
    }

    public MySpliterator(List<User> users, int characteristics) {
        this(users,0,users.size() - 1,characteristics);
    }

    private MySpliterator(List<User> users, int startIndex, int ednIndex) {
        this.users = users;
        this.startIndex = startIndex;
        this.ednIndex = ednIndex;
    }

    private MySpliterator(List<User> users, int startIndex, int ednIndex, int characteristics) {
        this.users = users;
        this.startIndex = startIndex;
        this.ednIndex = ednIndex;
        this.characteristics = characteristics;
    }



    @Override
    public MySpliterator trySplit() {
        if (startIndex > ednIndex) return null;
        int hi = (startIndex + ednIndex) / 2;
        int oldEnd = ednIndex;
        ednIndex = hi;
        return new MySpliterator(users,hi + 1,oldEnd);
    }


    @Override
    public long estimateSize() {
        return ednIndex - startIndex + 1;
    }

    @Override
    public int characteristics() {
        return this.characteristics;
    }

    @Override
    public boolean tryAdvance(Consumer action) {
        if (this.startIndex <= this.ednIndex){
            action.accept(users.get(startIndex++));
            return true;
        }
        return false;
    }
}
