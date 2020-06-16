package szzii.com.MyStream;

import java.util.function.Consumer;

/**
 * @author szz
 */
public interface Sink<T> extends Consumer<T> {


    default void begin(long size) {}


    default void end() {}


    abstract class ChainedReference<T,E_OUT> implements Sink<E_OUT>{
        protected final Sink<? super E_OUT> downstream;

        public ChainedReference(Sink<? super E_OUT> downstream) {
            this.downstream = downstream;
        }

        @Override
        public void begin(long size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }

    }

}
