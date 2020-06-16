package szzii.com.MyStream;

import java.util.function.Supplier;

/**
 * @author szz
 */
public interface TerminalSink <T, R> extends Sink<T>, Supplier<R> {
}
