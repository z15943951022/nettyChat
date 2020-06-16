package szzii.com;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author szz
 */
public class TestThreadLocal {

    private static ThreadLocal<SimpleDateFormat[]> formats = new ThreadLocal<SimpleDateFormat[]>() {
        @Override
        protected SimpleDateFormat[] initialValue() {
            return new SimpleDateFormat[]{
                    new SimpleDateFormat("yyyy-MM"),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("yyyy-MM-dd HH"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            };
        }
    };

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            executorService.execute(()->{
                formats.remove();
            });
        }
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < 100; i++) {
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+formats.get());
            });
        }
        System.out.println(Thread.currentThread().getName()+formats.get());
        formats.set(null);
        System.out.println(Thread.currentThread().getName()+formats.get());



    }
}
