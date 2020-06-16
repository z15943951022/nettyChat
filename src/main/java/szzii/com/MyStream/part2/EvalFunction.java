package szzii.com.MyStream.part2;

/**
 * @author szz
 */
@FunctionalInterface
public interface EvalFunction<T> {

    MyStream<T> apply();

}
