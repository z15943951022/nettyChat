package szzii.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author szz
 */
public class TestMain {

    private static List<Object> list = new ArrayList<>();
    static {
        list.add("张三");
        list.add("李四");
        list.add("王五");
    }


    public static <T extends Object> void function(Consumer<T> consumer){
        consumer.accept((T)list);
    }

    public static void print(){
        System.out.println("====打印结果:");
        list.stream().forEach(System.out::println);
    }

    public static void main(String[] args) {
        print();
        // 添加函数
        TestMain.function(new Consumer<Consumer>() {
            @Override
            public void accept(Consumer consumer) {
                consumer.accept(list);
            }
        });
        print();
        // 删除
        TestMain.function(new Consumer<List<Object>>() {
            @Override
            public void accept(List<Object> objects) {
                objects.remove("类程序");
            }
        });
        print();


    }
}
