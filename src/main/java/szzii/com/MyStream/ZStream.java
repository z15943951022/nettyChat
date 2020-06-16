package szzii.com.MyStream;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * @author szz
 */
public interface ZStream<T> {

    ZStream<T> filter(Predicate<T> predicate);

    <R> ZStream<R> map(Function<? super T, ? extends R> mapper);

    <R, A> R collect(Collector<? super T, A, R> collector);

}
