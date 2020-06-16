package szzii.com.MyStream;

import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;


public abstract class ReferencePipeline<E_IN,E_OUT> implements ZStream<E_OUT> {

    private ReferencePipeline nextStage;

    private final ReferencePipeline previousStage;

    private final ReferencePipeline sourceStage;

    private final Spliterator sourceSpliterator;

    private Integer depth;

    public ReferencePipeline(Spliterator sourceSpliterator) {
        this.sourceSpliterator = sourceSpliterator;
        this.sourceStage = this;
        this.previousStage = null;
        this.nextStage = null;
        this.depth = 0;
    }

    public ReferencePipeline(ReferencePipeline previousStage,ReferencePipeline sourceStage, Spliterator sourceSpliterator) {
        previousStage.nextStage = this;
        this.previousStage = this;
        this.sourceStage = sourceStage;
        this.sourceSpliterator = sourceSpliterator;
        this.depth = previousStage.depth + 1;
    }

    static class Head<E_IN,E_OUT> extends ReferencePipeline<E_IN,E_OUT>{
        public Head(Spliterator sourceSpliterator) {
            super(sourceSpliterator);
        }

        @Override
        Sink<E_IN> opWrapSink(Sink<E_OUT> sink) {
            throw new UnsupportedOperationException();
        }
    }


    static abstract class StatelessOp<E_IN,E_OUT> extends ReferencePipeline<E_IN,E_OUT> {
        public StatelessOp(ReferencePipeline currentStage,ReferencePipeline sourceStage,Spliterator sourceSpliterator) {
            super(currentStage,sourceStage,sourceSpliterator);
        }
    }

    @Override
    public ZStream<E_OUT> filter(Predicate<E_OUT> predicate) {
        return null;
    }


    @Override
    public <R> ZStream<R> map(Function<? super E_OUT, ? extends R> mapper) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super E_OUT, A, R> collector) {
        Function<A, R> finisher = collector.finisher();
        for (ReferencePipeline p = ReferencePipeline.this; p.depth > 0; p=p.previousStage) {
                p.opWrapSink(new TerminalSink() {
                    @Override
                    public Object get() {
                        return null;
                    }
                    @Override
                    public void accept(Object o) {
                    }
            });
        }
        return null;
    }

    abstract Sink<E_IN> opWrapSink(Sink<E_OUT> sink);

}
