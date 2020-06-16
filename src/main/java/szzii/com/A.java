package szzii.com;

import java.util.function.Function;

/**
 * @author szz
 */
public class A {

    public static void a(Function function){
        String a = "a";
        // 这里就是回调
        //function.apply(a);
    }


    public static void main(String[] args) {
        A.a(new Function() {
            @Override
            public Object apply(Object o) {
                return "哈哈";
            }
        });
    }
}
