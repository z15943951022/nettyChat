package szzii.com.MyStream.part2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author szz
 */
public class MyStream<T> implements Stream<T> {

    private T head;

    private NextItemEvalProcess nextItemEvalProcess;

    private boolean isEnd;

    @Override
    public <R> MyStream<R> map(Function<R, T> mapper) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()->{
                    MyStream myStream = lastNextItemEvalProcess.eval();
                    return map(mapper, myStream);
                }
        );

        // 求值链条 加入一个新的process map
        return new MyStream.Builder<R>()
                .nextItemEvalProcess(this.nextItemEvalProcess)
                .build();
    }

    /**
     * 递归函数 配合API.map
     * */
    private static <R,T> MyStream<R> map(Function<R, T> mapper, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        R head = (R) mapper.apply((R) myStream.head);

        return new MyStream.Builder<R>()
                .head(head)
                .nextItemEvalProcess(new NextItemEvalProcess(()->map(mapper, myStream.eval())))
                .build();
    }

    @Override
    public <R> MyStream<R> flatMap(Function<? extends MyStream<R>, T> mapper) {
        return null;
    }

    @Override
    public MyStream<T> filter(Predicate<T> predicate) {
        return null;
    }

    @Override
    public MyStream<T> limit(int n) {
        return null;
    }

    @Override
    public MyStream<T> distinct() {
        return null;
    }

    @Override
    public MyStream<T> peek(Consumer<T> consumer) {
        return null;
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        forEach(consumer,this.eval());
    }

    /**
     * 递归函数 配合API.forEach
     * */
    private static <T> void forEach(Consumer<T> consumer, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return;
        }

        consumer.accept(myStream.head);
        forEach(consumer, myStream.eval());
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public T min(Comparator<T> comparator) {
        return null;
    }

    @Override
    public T max(Comparator<T> comparator) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        return null;
    }

    @Override
    public <R> R reduce(R initVal, BiFunction<R, R, T> accumulator) {
        return null;
    }

    public static class Builder<T>{
        private MyStream<T> target;

        public Builder() {
            this.target = new MyStream<>();
        }

        public Builder<T> head(T head){
            target.head = head;
            return this;
        }

        Builder<T> isEnd(boolean isEnd){
            target.isEnd = isEnd;
            return this;
        }

        public Builder<T> nextItemEvalProcess(NextItemEvalProcess next){
            target.nextItemEvalProcess = next;
            return this;
        }

        public MyStream<T> build(){
            return target;
        }
    }

    private MyStream<T> eval(){
        return this.nextItemEvalProcess.eval();
    }

    private boolean isEmptyStream(){
        return this.isEnd;
    }


}
