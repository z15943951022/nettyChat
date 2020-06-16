package szzii.com.MyStream;

import java.util.Objects;
import java.util.Spliterator;

/**
 * @author szz
 */
public class StreamSupport {

    public static <T> ZStream<T> stream(Spliterator<T> spliterator) {
        Objects.requireNonNull(spliterator);
        return new ReferencePipeline.Head<>(spliterator);
    }
}
